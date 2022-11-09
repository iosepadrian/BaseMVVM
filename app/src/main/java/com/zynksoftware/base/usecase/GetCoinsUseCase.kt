package com.zynksoftware.base.usecase

import com.zynksoftware.base.network.common.Resource
import com.zynksoftware.base.repository.CoinsRepository
import kotlinx.coroutines.flow.flow

class GetCoinsUseCase(private val repository: CoinsRepository) {

    suspend fun getCoins() = flow {
        emit(Resource.Loading())
        val response = repository.getCoins()
        emit(response)
    }

}