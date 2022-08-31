package ru.netology.nmedia

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0,
    val views: Int = 0,
    var videoUrl : String? = null
) {

    fun likesToString() = valueFormatter(likes)
    fun shareToString() = valueFormatter(share)
    fun viewsToString() = valueFormatter(views)

    private fun valueFormatter(count: Int) : String =
        when (count) {
            0 -> ""
            in 1..999 -> count.toString()
            in 1000..999_999 -> String.format("%.1f", count / 1000.0) + "K"
            else -> String.format("%.1f", count / 1_000_000.0) + "M"
        }
}