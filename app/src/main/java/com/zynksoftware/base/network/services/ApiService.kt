package com.zynksoftware.base.network.services

import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.models.RegisterResponse
import com.zynksoftware.base.network.common.AuthorizationType
import com.zynksoftware.base.network.common.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface ApiService {

    @POST("/oauth/token")
    fun refreshToken(@Query("grant_type") grantType: String = "refresh_token",
                     @Query("refresh_token") refreshToken: String,
                     @Tag authorization: AuthorizationType = AuthorizationType.CLIENT_CREDENTIALS): Call<TokenResponse>

    @POST("api/v1/sessions/login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): Response<TokenResponse>

    @POST("api/v1/account/register")
    suspend fun register(
        @Body registerRequestBody: RegisterRequestBody
    ): Response<RegisterResponse>

}