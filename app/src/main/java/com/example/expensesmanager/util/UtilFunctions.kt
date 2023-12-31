package com.example.expensesmanager.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToPattern(zonedDateTime: ZonedDateTime, pattern: String): String {
    return zonedDateTime.format(DateTimeFormatter.ofPattern(pattern)).toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}