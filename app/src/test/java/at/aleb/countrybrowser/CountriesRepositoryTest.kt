package at.aleb.countrybrowser

import at.aleb.countrybrowser.data.CountriesRepository
import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.data.myData
import at.aleb.countrybrowser.data.myErrors
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.util.Samples
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

class CountriesRepositoryTest {
    lateinit var apolloClient: ApolloClient
    lateinit var apolloResponse: ApolloResponse<CountriesQuery.Data>
    lateinit var apolloCall: ApolloCall<CountriesQuery.Data>

    @Before
    fun before() {
        apolloClient = mockk()
        apolloResponse = mockk()
        apolloCall = mockk()
        every { apolloClient.query<CountriesQuery.Data>(any()) } returns apolloCall
        coEvery { apolloCall.execute() } returns apolloResponse
        mockkStatic("at.aleb.countrybrowser.data.ApolloExtensionKt")
    }

    @Test
    fun `with ApolloNetworkException returns NOCONNECTION`(): Unit = runBlocking {
        coEvery { apolloCall.execute() } throws ApolloNetworkException()

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assert(result is Resource.NOCONNECTION)
    }

    @Test
    fun `with null data returns ERROR`(): Unit = runBlocking {
        every { apolloResponse.hasErrors() } returns false
        every { apolloResponse.myData } answers { null }

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.ERROR::class, result::class)
    }

    @Test
    fun `with empty list returns NOTFOUND`(): Unit = runBlocking {
        every { apolloResponse.hasErrors() } returns false
        every { apolloResponse.myData } answers { CountriesQuery.Data(listOf()) }

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.NOTFOUND::class, result::class)
    }

    @Test
    fun `with errors returns ERROR`(): Unit = runBlocking {
        every { apolloResponse.hasErrors() } returns true
        every { apolloResponse.myErrors } answers { listOf() }

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.ERROR::class, result::class)
    }

    @Test
    fun `with data and no errors returns SUCCESS and data`(): Unit = runBlocking {
        every { apolloResponse.hasErrors() } returns false
        every { apolloResponse.myData } returns CountriesQuery.Data(Samples.countriesDtoList)

        val repo = CountriesRepository(apolloClient)
        val result = repo.getCountries()

        assertEquals(Resource.SUCCESS::class, result::class)
        assertEquals(Samples.countriesList, (result as Resource.SUCCESS).data)
    }
}