package dev.azeredo.presentation
import com.dokar.sonner.Toast
import com.dokar.sonner.ToastType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun getDecimalRegex(): Regex {
    return """\d*\.?\d*""".toRegex()
}

sealed class UiMessage(val id: Long, val message: String) {
    class Success(id: Long, message: String) : UiMessage(id, message)
    class Error(id: Long, message: String) : UiMessage(id, message)
}

fun UiMessage.toToast(): Toast = when (this) {
    is UiMessage.Error -> Toast(id = id, message = message, type = ToastType.Error)
    is UiMessage.Success -> Toast(id = id, message = message, type = ToastType.Success)
}

fun getDateTimeFormated(date: Long): String {
    val instant = Instant.fromEpochMilliseconds(date)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Format {
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    )
}