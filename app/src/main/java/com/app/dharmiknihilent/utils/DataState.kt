package com.app.dharmiknihilent.utils

sealed interface DataState<out T> {

    data class Success<T>(val data: T) : DataState<T>

    data class Error(val message: String) : DataState<Nothing>

    object Data : DataState<Nothing>

}