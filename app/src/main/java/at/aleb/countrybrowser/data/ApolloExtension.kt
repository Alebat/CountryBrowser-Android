package at.aleb.countrybrowser.data

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation

val <D: Operation.Data> ApolloResponse<D>.myData get() = data
val <D: Operation.Data> ApolloResponse<D>.myErrors get() = errors