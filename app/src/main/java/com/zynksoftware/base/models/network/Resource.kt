package com.zynksoftware.base.models.network

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

sealed class Resource<T>(
    val httpCode: Int? = null,
    val errorTitle: String? = null,
    val data: T? = null,
    val message: String? = null,
    val status: Status,
    val fullError: JSONObject? = null,
    val allErrors: JSONArray? = null
): Serializable {
    class Success<T>(data: T?, httpCode: Int) : Resource<T>(httpCode = httpCode, data = data, status = Status.SUCCESS)
    class Loading<T>(data: T? = null) : Resource<T>(data = data, status = Status.LOADING)
    class Error<T>(httpCode: Int? = null, title: String? = null, message: String?, fullError: JSONObject? = null, allErrors: JSONArray? = null) :
        Resource<T>(httpCode = httpCode, errorTitle = title, message = message, status = Status.ERROR, fullError = fullError, allErrors = allErrors)
}

fun <T> Resource<T>.isSuccessful(): Boolean {
    return status == Status.SUCCESS
}