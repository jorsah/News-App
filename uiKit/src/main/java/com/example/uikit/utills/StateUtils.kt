package com.example.uikit.utills

import androidx.compose.runtime.MutableState

fun <T> MutableState<T>.update(updatedState: (T) -> T) {
    value = updatedState(value)
}
