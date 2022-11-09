package com.zynksoftware.base.models

import com.squareup.moshi.Json

data class CryptoModel(
    val id: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val image: String? = null,
    @Json(name = "current_price") val currentPrice: Double? = null,
    @Json(name = "market_cap") val marketCap: Double? = null,
    @Json(name = "market_cap_rank") val marketCapRank: Double? = null,
    @Json(name = "total_volume") val totalVolume: Double? = null,
    @Json(name = "high_24h") val high24H: Double? = null,
    @Json(name = "low_24h") val low24H: Double? = null,
    @Json(name = "price_change_24h") val priceChange24H: Double? = null,
    @Json(name = "price_change_percentage_24h") val priceChangePercentage24H: Double? = null,
    @Json(name = "last_updated") val lastUpdated: String? = null,
)