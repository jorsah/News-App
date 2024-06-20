package com.example.newsapp.core.domain.usecases

import com.example.datastore.manager.DataStoreManager
import javax.inject.Inject

class GetListTypeUseCase @Inject constructor(private val dataStoreManager: DataStoreManager) {

    operator fun invoke() = dataStoreManager.getListType()
}
