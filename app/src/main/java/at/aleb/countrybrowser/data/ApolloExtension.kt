package at.aleb.countrybrowser.data

import at.aleb.countrybrowser.domain.Resource
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.exception.ApolloNetworkException

/***
 * Improves testability
 */
val <D : Operation.Data> ApolloResponse<D>.myData get() = data

/***
 * Improves testability
 */
val <D : Operation.Data> ApolloResponse<D>.myErrors get() = errors

suspend fun <D : Query.Data, Q : Query<D>, R> ApolloClient.manageQuery(
    query: Q,
    toEntity: (dto: D) -> R?
) =
    try {
        val result = this.query(query).execute()
        val data = result.myData
        if (result.hasErrors() || data == null)
            Resource.ERROR(result.myErrors.toString())
        else when (val entity = toEntity(data)) {
            null -> Resource.NOTFOUND()
            else -> Resource.SUCCESS(entity)
        }
    } catch (e: ApolloNetworkException) {
        Resource.NOCONNECTION()
    }
