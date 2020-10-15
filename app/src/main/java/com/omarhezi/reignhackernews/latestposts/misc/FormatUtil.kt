package com.omarhezi.reignhackernews.latestposts.misc

import android.icu.text.TimeZoneFormat
import android.provider.Settings.System.DATE_FORMAT
import com.omarhezi.reignhackernews.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class FormatUtil(private val stringUtil: StringUtil) {
    fun formatCreatedAtString(dateString: String?): String {

        if (dateString == null) return stringUtil.getString(R.string.unknown_time)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val storyDate = dateFormat.parse(dateString)!!

        val diff = Date().time - storyDate.time
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diff)
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diff)
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff)

        return when {
            diffInSeconds < 60 -> "${diffInSeconds}${stringUtil.getString(R.string.second_abbreviation)}"
            diffInMinutes < 60 -> "${diffInMinutes}${stringUtil.getString(R.string.minute_abbreviation)}"
            diffInHours in 1..23 -> {
                var hours = (diffInMinutes / 60).toString()
                val minutes = (diffInMinutes % 60)
                if (minutes >= 30) hours += ".5"
                "${hours}${stringUtil.getString(R.string.hour_abbreviation)}"
            }
            diffInDays == 1L -> stringUtil.getString(R.string.yesterday)
            diffInDays in 1..7 -> stringUtil.getString(R.string.last_week)
            diffInDays in 7..30 -> stringUtil.getString(R.string.last_month)
            diffInDays > 30 -> stringUtil.getString(R.string.long_time)
            else -> stringUtil.getString(R.string.unknown_time)
        }

    }
}