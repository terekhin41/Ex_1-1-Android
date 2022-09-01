package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.db.PostDao

class SQLiteRepository(
    private val dao: PostDao
) : PostRepository {

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(dao.getAll())

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0L) {
            listOf(saved) + posts
        } else posts.map {
                if (it.id != id) it else saved
        }
    }

    override fun like(postId: Long) {
        dao.likeById(postId)
        data.value = posts.map {
            if (it.id != postId) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun delete(postId: Long) {
        dao.removeById(postId)
        data.value = posts.filter { it.id != postId }
    }

    override fun getPostById(id: Long): Post? {
        return try {
            dao.getPostById(id)
        } catch (ex: Exception) {
            null
        }
    }

    override fun share(postId: Long) {
        dao.share(postId)
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(share = it.share + 1)
        }
    }
}