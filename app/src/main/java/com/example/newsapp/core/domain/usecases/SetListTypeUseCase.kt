package com.example.newsapp.core.domain.usecases

import com.example.datastore.manager.DataStoreManager
import com.example.datastore.model.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetListTypeUseCase @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend operator fun invoke(listType: ListType) = withContext(Dispatchers.IO) {
        dataStoreManager.setListType(listType)
    }
}
