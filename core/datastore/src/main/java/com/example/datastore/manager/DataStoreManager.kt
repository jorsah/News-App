package com.example.datastore.manager

import com.example.datastore.model.ListType
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun setListType(listType: ListType)
    fun getListType(): Flow<ListType>
}