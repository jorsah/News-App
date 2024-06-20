package com.example.datastore.model

import androidx.annotation.StringRes
import com.example.datastore.R

enum class ListType(@StringRes val nameRes: Int) {
    GRID_LIST(R.string.grid_list_option), COLUMN_LIST(R.string.simple_column_option)
}