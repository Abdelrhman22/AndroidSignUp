package com.task.androidtask.utilities

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, error = null)
        }

        fun <T> error(error: Throwable?): Resource<T> {
            return Resource(status = Status.ERROR, data = null, error = error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(status = Status.LOADING, data = null, error = null)
        }
    }
}
