package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun share(postId: Long)

    fun delete(postId: Long)

    fun save(post: Post)

    fun getPostById(id: Long) : Post?

    companion object {
        const val NEW_POST_ID = 0L
    }
}