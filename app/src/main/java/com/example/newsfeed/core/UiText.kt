package com.example.newsfeed.core

import android.content.Context
import androidx.annotation.StringRes
import com.example.newsfeed.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    /*Used to call at Unit Test domain*/
    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.error_unknown)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> this.value
            is StringResource -> context.getString(this.id)
        }
    }
}
