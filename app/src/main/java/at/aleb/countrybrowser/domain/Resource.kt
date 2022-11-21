package at.aleb.countrybrowser.domain

sealed class Resource<T> {
    class EMPTY<T>: Resource<T>()
    class LOADING<T>: Resource<T>()
    data class SUCCESS<T>(val data: T) : Resource<T>()
    class NOTFOUND<T> : Resource<T>()
    class NOCONNECTION<T> : Resource<T>()
    data class ERROR<T>(val message: String) : Resource<T>()
}