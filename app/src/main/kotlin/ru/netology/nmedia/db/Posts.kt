package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likedByMe = likedByMe,
    likes = likes,
    share = share
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likedByMe = likedByMe,
    likes = likes,
    share = share
)