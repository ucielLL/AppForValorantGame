package com.example.team_val.di

import com.example.team_val.data.network.InterceptorRioApi
import com.example.team_val.data.network.RioApi
import com.example.team_val.data.network.ValorantApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.math.BigDecimal
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitRio
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitValorant

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

@RetrofitRio
@Singleton
@Provides
fun providerRetrifitRio():Retrofit{
    return Retrofit.Builder()
        .baseUrl("https://americas.api.riotgames.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(InterceptorRioApi()).build()
        )
        .build()
}
    @Singleton
    @Provides
    fun providerRioApi( @RetrofitRio retrofit: Retrofit):RioApi{
        return retrofit.create(RioApi::class.java)
    }
   @RetrofitValorant
   @Singleton
   @Provides
   fun providerRetrofitValorant():Retrofit{
       return return Retrofit.Builder()
           .baseUrl("https://valorant-api.com/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
   }
    @Singleton
    @Provides
    fun providerValirantApi(@RetrofitValorant retrofit: Retrofit ):ValorantApi{
        return retrofit.create(ValorantApi::class.java)
    }






}
