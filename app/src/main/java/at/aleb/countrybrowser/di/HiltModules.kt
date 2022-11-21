package at.aleb.countrybrowser.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    fun provideApolloClient() =
        ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/")
            .build()

    @Provides
    @QualifyDispatcher.IO
    fun provideIODispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}

class QualifyDispatcher {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IO
}
