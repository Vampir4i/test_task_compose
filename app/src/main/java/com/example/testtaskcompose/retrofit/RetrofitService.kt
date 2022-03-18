package com.example.testtaskcompose.retrofit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") perPage: Int = 10,
    ): Response<List<CommonProfile>>

    @GET("users/{user_name}")
    suspend fun getUser(
        @Path("user_name") userName: String
    ): Response<GitProfile>

    @GET("users/{user_name}/repos")
    suspend fun getUsersRepos(
        @Path("user_name") userName: String
    ): Response<List<CommonRepo>>

    @GET("users/{user_name}/gists")
    suspend fun getUsersGists(
        @Path("user_name") userName: String
    ): Response<List<CommonGist>>

    @GET("users/{user_name}/followers")
    suspend fun getUsersFollowers(
        @Path("user_name") userName: String
    ): Response<List<CommonProfile>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if(retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}