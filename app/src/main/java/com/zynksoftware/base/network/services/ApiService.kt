package com.zynksoftware.base.network.services

import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.models.RegisterResponse
import com.zynksoftware.base.models.Role
import com.zynksoftware.base.models.RoleRequest
import com.zynksoftware.base.models.SuccessResponse
import com.zynksoftware.base.network.common.AuthorizationType
import com.zynksoftware.base.network.common.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface ApiService {

    @POST("api/v1/sessions/refresh-token")
    fun refreshToken(
        @Header("Refresh-token") refreshToken: String,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Call<TokenResponse>

    @POST("api/v1/sessions/login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<TokenResponse>

    @POST("api/v1/account/register")
    suspend fun register(
        @Body registerRequestBody: RegisterRequestBody,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<RegisterResponse>

    @POST("api/v1/sessions/logout/current")
    suspend fun logout(): Response<SuccessResponse>

    @POST("api/v1/account/forgot-password")
    suspend fun forgotPassword(
        @Query("email") email: String?,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Response<SuccessResponse>

    @GET("api/v1/roles")
    suspend fun getRoles(): Response<List<Role>>

    @PATCH("api/v1/roles")
    suspend fun editRole(
        @Body roleRequestBody: RoleRequest
    ): Response<SuccessResponse>

}