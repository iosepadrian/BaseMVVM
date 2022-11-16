package com.zynksoftware.base.network.services

import com.zynksoftware.base.network.common.AuthorizationType
import com.zynksoftware.base.network.common.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface ApiService {

    @POST("/oauth/token")
    fun refreshToken(@Query("grant_type") grantType: String = "refresh_token",
                     @Query("refresh_token") refreshToken: String,
                     @Tag authorization: AuthorizationType = AuthorizationType.CLIENT_CREDENTIALS): Call<TokenResponse>

    @POST("/oauth/token")
    suspend fun login(
        @Query("username", encoded = true) username: String,
        @Query("password") password: String,
        @Query("grant_type") grantType: String = "password",
        @Tag authorization: AuthorizationType = AuthorizationType.CLIENT_CREDENTIALS
    ): Response<TokenResponse>

}