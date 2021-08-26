package ar.com.wolox.android.example.utils

import org.ocpsoft.prettytime.Duration
import org.ocpsoft.prettytime.TimeFormat

class CustomTimeFormat : TimeFormat {
    override fun format(duration: Duration): String {
        return Math.abs(duration.getQuantity()).toString() + "m"
    }

    override fun formatUnrounded(duration: Duration): String {
        return format(duration)
    }

    override fun decorate(duration: Duration?, time: String): String {
        return time
    }

    override fun decorateUnrounded(duration: Duration?, time: String): String {
        return time
    }
}