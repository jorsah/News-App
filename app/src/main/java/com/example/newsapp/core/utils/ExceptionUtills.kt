package com.example.newsapp.core.utils

import java.net.UnknownHostException

fun Throwable.isNetworkReachable(
    onAvailable: () -> Unit,
    onError: () -> Unit
) {
    if (this is UnknownHostException) onError() else onAvailable()
}
