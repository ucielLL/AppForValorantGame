package com.example.team_val

sealed class ResponseData < out T>{
    data class Success<T>(val data: T): ResponseData<T>()
    data class Error(val messageError: String): ResponseData<Nothing>()

}
