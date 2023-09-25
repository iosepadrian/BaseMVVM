package com.zynksoftware.base.network.common

import android.content.SharedPreferences
import com.zynksoftware.base.network.services.ApiService
import dagger.Lazy
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val apiService: Lazy<ApiService>
) {

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN_EXPIRES_IN_KEY = "TOKEN_EXPIRES_IN_KEY"
    }

    private fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    private fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    private fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    /**
     * Function signatures of both Interceptor and Authenticator require the request to be created or transformed synchronously.
     * The process of refreshing an access token is likely asynchronous due to requiring its own web request to an OAuth API.
     * We therefore need to call this asynchronous token refresh process synchronously
     */
    fun fetchNewTokenCall(): String? {
        val refreshTokenResponse = apiService.get().refreshToken(refreshToken = getRefreshToken()).execute()
        return if (refreshTokenResponse.isSuccessful) {
            saveToken(refreshTokenResponse.body()!!)
            getAccessToken()
        } else {
            null
        }
    }

    fun isAccessTokenExpired(): Boolean {
        val tokenExpirationTime = sharedPreferences.getLong(ACCESS_TOKEN_EXPIRES_IN_KEY, 0.toLong())
        return if(tokenExpirationTime == 0.toLong()) {
            true
        } else System.currentTimeMillis() >= tokenExpirationTime
    }

    private fun setAccessTokenExpiresIn(expiresIn: Long) {
        sharedPreferences.edit().putLong(ACCESS_TOKEN_EXPIRES_IN_KEY, (System.currentTimeMillis() + expiresIn).toLong()).apply()
    }

    fun saveToken(tokenResponse: TokenResponse) {
        if (tokenResponse.accessToken != null) {
            val token = tokenResponse.accessToken
            if (token != null) {
                if (token.trim().isNotEmpty()) {
                    setAccessToken(token)
                }
            }
        }
        tokenResponse.refreshToken?.let { setRefreshToken(it) }
        tokenResponse.validityMs?.let { setAccessTokenExpiresIn(it) }
    }

    fun logout() {
        setAccessToken("")
        setRefreshToken("")
        setAccessTokenExpiresIn(0L)
    }
}