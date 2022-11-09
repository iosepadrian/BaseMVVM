package com.zynksoftware.base.repository

import com.zynksoftware.base.models.CryptoModel
import com.zynksoftware.base.network.services.ApiService
import com.zynksoftware.base.network.RemoteServicesHandler
import com.zynksoftware.base.network.common.Resource

class CoinsRepository(
    private val apiService: ApiService,
    private val servicesHandler: RemoteServicesHandler
) {

    suspend fun getCoins(): Resource<List<CryptoModel>> =
        servicesHandler.makeTheCallAndHandleResponse(
            serviceCall = {
                apiService.getCoins()
            },
            mapSuccess = { Resource.Success(it.body(), it.code()) }
        )

}