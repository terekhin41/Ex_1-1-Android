package ru.netology.nmedia.service

import com.google.gson.annotations.SerializedName

class NewPostDataByService (
    @SerializedName("id")
    val id: Long,

    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("videoUrl")
    val videoUrl: String?,

    @SerializedName("published")
    val published: String,

    @SerializedName("likedByMe")
    val likedByMe: Boolean,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("share")
    val share: Int,

    @SerializedName("views")
    val views: Int
)