package com.example.datastore.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.datastore.model.ListType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreManager {

    companion object {
        private const val LIST_TYPE = "isGridList"
        val isGridList = booleanPreferencesKey(LIST_TYPE)
    }

    override suspend fun setListType(listType: ListType) {
        dataStore.edit { preference ->
            preference[isGridList] = listType == ListType.GRID_LIST
        }
    }

    override fun getListType(): Flow<ListType> {
        return dataStore.data.map {
            if (it[isGridList] == true) ListType.GRID_LIST
            else ListType.COLUMN_LIST
        }
    }
}
