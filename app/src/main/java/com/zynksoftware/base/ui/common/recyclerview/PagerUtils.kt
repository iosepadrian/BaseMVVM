package com.zynksoftware.base.ui.common.recyclerview

import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

private const val DEFAULT_PAGE_SIZE = 10
private const val DEFAULT_PREFETCH_DISTANCE = 3

fun <V : Any> createPager(
    pageSize: Int = DEFAULT_PAGE_SIZE,
    enablePlaceholders: Boolean = false,
    prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE,
    block: suspend (page: Int, size: Int) -> MutableList<V>
): Pager<Int, V> = Pager(
    config = PagingConfig(enablePlaceholders = enablePlaceholders, pageSize = pageSize, prefetchDistance = prefetchDistance),
    pagingSourceFactory = { BasePagingSource(block) }
)

fun <T: Any> Flow<PagingData<T>>.combineForEvent(
    scope: CoroutineScope,
    modificationEvents: MutableStateFlow<List<PagingEvent<T>>>,
    deleteBlock: (first: T, second: T) -> Boolean,
    editBlock: (first: T, second: T) -> T): Flow<PagingData<T>> {
    return this
        .cachedIn(scope)
        .combine(modificationEvents) { pagingData: PagingData<T>, modifications ->
            modifications.fold(pagingData) { newPagingData, event ->
                applyEvents(newPagingData, event, deleteBlock, editBlock)
            }
        }
}

sealed class PagingEvent<T> {
    data class Edit<T>(val entity: T) : PagingEvent<T>()
    data class Delete<T>(val entity: T) : PagingEvent<T>()
}

 fun <T: Any> applyEvents(
    paging: PagingData<T>,
    event: PagingEvent<T>,
    deleteBlock: (first: T, second: T) -> Boolean,
    editBlock: (first: T, second: T) -> T
): PagingData<T> {
    return when (event) {
        is PagingEvent.Delete<T> -> {
            paging
                .filter {
                    deleteBlock.invoke(event.entity, it)
                }
        }
        is PagingEvent.Edit<T> -> {
            paging
                .map {
                    return@map editBlock.invoke(event.entity, it)
                }
        }
    }
}