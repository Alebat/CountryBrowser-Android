package at.aleb.countrybrowser.data

import at.aleb.countrybrowser.data.graphql.CountryDetailQuery
import at.aleb.countrybrowser.domain.CountryDetails
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.domain.toEntity
import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject

class CountryDetailsRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getDetails(code: String): Resource<CountryDetails> =
        apolloClient.manageQuery(CountryDetailQuery(code)) { dto ->
            dto.country?.toEntity()
        }
}
