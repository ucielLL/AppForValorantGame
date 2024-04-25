package com.example.team_val.data.network

import okhttp3.Interceptor
import okhttp3.Response

class InterceptorRioApi:Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString() + "?api_key=RGAPI-28c987b6-2386-4e18-bc74-797e79edb978"
        val response = chain.
        request().newBuilder().url(url).build()

        return chain.proceed(response)
    }


}