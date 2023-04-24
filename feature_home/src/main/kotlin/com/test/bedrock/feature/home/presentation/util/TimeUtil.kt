package com.test.bedrock.feature.home.presentation.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    internal fun formatDateTime(time: String): String {
        val date = LocalDateTime.parse(time)
        val formatter = DateTimeFormatter.ofPattern("EE dd MMM yyyy, HH:mm")
        return formatter.format(date)
    }
}
