package at.aleb.countrybrowser

import at.aleb.countrybrowser.data.CountryDetailsRepository
import at.aleb.countrybrowser.data.graphql.CountryDetailQuery
import at.aleb.countrybrowser.data.myData
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.util.Samples
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CountryDetailsRepositoryTest {
    lateinit var apolloClient: ApolloClient
    lateinit var apolloCall: ApolloCall<CountryDetailQuery.Data>
    lateinit var apolloResponse: ApolloResponse<CountryDetailQuery.Data>
    lateinit var repository: CountryDetailsRepository

    @Before
    fun before() {
        apolloResponse = mockk()
        apolloCall = mockk()
        apolloClient = mockk()

        every { apolloResponse.hasErrors() } returns false
        coEvery { apolloCall.execute() } returns apolloResponse
        every { apolloClient.query<CountryDetailQuery.Data>(any()) } returns apolloCall

        repository = CountryDetailsRepository(apolloClient)

        mockkStatic("at.aleb.countrybrowser.data.ApolloExtensionKt")
    }

    @Test
    fun `with null data returns ERROR`(): Unit = runBlocking {
        every { apolloResponse.myData } returns null

        val result = repository.getDetails("IT")

        assertEquals(Resource.ERROR::class, result::class)
    }

    @Test
    fun `with null entity returns NOTFOUND`(): Unit = runBlocking {
        every { apolloResponse.myData } returns CountryDetailQuery.Data(null)

        val result = repository.getDetails("IT")

        assertEquals(Resource.NOTFOUND::class, result::class)
    }

    @Test
    fun `with data and no errors returns SUCCESS and entity`(): Unit = runBlocking {
        every { apolloResponse.myData } returns
                CountryDetailQuery.Data(Samples.countryDetailDto)

        val result = repository.getDetails("IT")

        assertEquals(Resource.SUCCESS::class, result::class)
        assertEquals(Samples.countryDetail, (result as Resource.SUCCESS).data)
    }
}