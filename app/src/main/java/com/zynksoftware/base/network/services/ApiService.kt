package com.zynksoftware.base.network.services

import com.zynksoftware.base.models.CryptoModel
import com.zynksoftware.base.network.common.AuthorizationType
import com.zynksoftware.base.network.common.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface ApiService {

    @GET("api/v3/coins/markets")
    suspend fun getCoins(
        @Tag authorization: AuthorizationType = AuthorizationType.NONE,
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 250,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = true,
        @Query("price_change_percentage") priceChangePercentage: String = "24h"
    ): Response<List<CryptoModel>>

    @POST("/oauth/token")
    fun refreshToken(@Query("grant_type") grantType: String = "refresh_token",
                     @Query("refresh_token") refreshToken: String,
                     @Tag authorization: AuthorizationType = AuthorizationType.CLIENT_CREDENTIALS): Call<TokenResponse>

}