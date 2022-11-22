package at.aleb.countrybrowser.data

import at.aleb.countrybrowser.data.graphql.CountriesQuery
import at.aleb.countrybrowser.domain.Country
import at.aleb.countrybrowser.domain.Resource
import at.aleb.countrybrowser.domain.toEntity
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloNetworkException
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    private suspend fun <D: Query.Data, T: Query<D>, R>query(clazz: Class<T>, toEntity: (dto: D) -> R) =
        try {
            val result = apolloClient.query(
                clazz.newInstance()
            ).execute()
            when {
                result.hasErrors() -> Resource.ERROR(result.myErrors.toString())
                result.myData == null -> Resource.NOTFOUND()
                else -> Resource.SUCCESS(toEntity(result.myData!!))
            }
        } catch (e: ApolloNetworkException) {
            Resource.NOCONNECTION()
        }

    suspend fun getCountries(): Resource<List<Country>> =
        query(CountriesQuery::class.java) { dto ->
            dto.countries.map { it.toEntity() }
        }
}