package at.aleb.countrybrowser.domain

sealed class Resource<T> {
    class Empty<T>: Resource<T>()
    class Loading<T>: Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    class NotFound<T> : Resource<T>()
    class NoConnection<T> : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}