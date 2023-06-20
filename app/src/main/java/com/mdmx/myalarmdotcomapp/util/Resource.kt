package com.mdmx.myalarmdotcomapp.util

sealed class Resource<T>(val data: T?, val massage: String?) {
    class Success<T>(  data: T) : Resource<T>(data, null)
    class Error<T>(massage: String) : Resource<T>(null, massage)
}