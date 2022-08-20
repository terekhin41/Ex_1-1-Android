package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var share: Int = 0,
    var views: Int = 0
) {

    fun like() {
        likedByMe = !likedByMe
        if (likedByMe) likes += 1 else likes -= 1
    }

    fun share() {
        share++
    }

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