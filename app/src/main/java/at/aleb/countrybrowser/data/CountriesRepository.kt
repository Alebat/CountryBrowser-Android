package at.aleb.countrybrowser.data

import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.domain.toEntity
import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getCountries(): Resource<List<Country>> =
        apolloClient.manageQuery(CountriesQuery()) { dto ->
            dto.countries.map { it.toEntity() }.ifEmpty { null }
        }
}
