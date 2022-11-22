package at.aleb.countrybrowser

import at.aleb.countrybrowser.data.CountriesRepository
import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.data.myData
import at.aleb.countrybrowser.data.myErrors
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.domain.toEntity
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloNetworkException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CountriesRepositoryTest {
    lateinit var apolloClient: ApolloClient

    @Before
    fun before() {
        apolloClient = mockk()
    }

    @Test
    fun `with ApolloNetworkException returns NOCONNECTION`(): Unit = runBlocking {
        coEvery { apolloClient.query<CountriesQuery.Data>(any()) } throws ApolloNetworkException()

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assert(result is Resource.NOCONNECTION)
    }

    @Test
    fun `with null data returns NOTFOUND`(): Unit = runBlocking {
        mockkStatic("at.aleb.countrybrowser.data.ApolloExtensionKt")

        val apolloResponse: ApolloResponse<CountriesQuery.Data> = mockk()
        val apolloCall: ApolloCall<CountriesQuery.Data> = mockk()
        every { apolloClient.query<CountriesQuery.Data>(any()) } returns apolloCall
        coEvery { apolloCall.execute() } returns apolloResponse
        every { apolloResponse.hasErrors() } returns false
        every { apolloResponse.myData } answers { null }

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.NOTFOUND::class, result::class)
    }

    @Test
    fun `with errors returns ERROR`(): Unit = runBlocking {
        mockkStatic("at.aleb.countrybrowser.data.ApolloExtensionKt")

        val apolloResponse: ApolloResponse<CountriesQuery.Data> = mockk()
        val apolloCall: ApolloCall<CountriesQuery.Data> = mockk()
        every { apolloClient.query<CountriesQuery.Data>(any()) } returns apolloCall
        coEvery { apolloCall.execute() } returns apolloResponse
        every { apolloResponse.hasErrors() } returns true
        every { apolloResponse.myErrors } answers { listOf() }

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.ERROR::class, result::class)
    }

    @Test
    fun `with data and no errors returns SUCCESS and data`(): Unit = runBlocking {
        mockkStatic("at.aleb.countrybrowser.data.ApolloExtensionKt")

        val apolloResponse: ApolloResponse<CountriesQuery.Data> = mockk()
        val apolloCall: ApolloCall<CountriesQuery.Data> = mockk()
        every { apolloClient.query<CountriesQuery.Data>(any()) } returns apolloCall
        coEvery { apolloCall.execute() } returns apolloResponse
        every { apolloResponse.hasErrors() } returns false
        every { apolloResponse.myData } returns CountriesQuery.Data(listOf(
            CountriesQuery.Country("IT", "Italy", "Italia", "Rome", "xyz"),
            CountriesQuery.Country("AT", "Austria", "Österreich", "Wien", "zyx"),
        ))

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.SUCCESS::class, result::class)
        assertEquals(listOf(
            CountriesQuery.Country("IT", "Italy", "Italia", "Rome", "xyz"),
            CountriesQuery.Country("AT", "Austria", "Österreich", "Wien", "zyx"),
        ).map { it.toEntity() }, (result as Resource.SUCCESS).data)
    }
}