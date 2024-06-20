package com.example.newsapp.core.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DatePatterns {
    const val DEFAULT_DATE_PATTERN = "dd/MM/yyyy"
}

fun String.formatDateString(): String {
    val zonedDateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)
    val outputFormatter = DateTimeFormatter.ofPattern(DatePatterns.DEFAULT_DATE_PATTERN)

    return zonedDateTime.format(outputFormatter)
}
