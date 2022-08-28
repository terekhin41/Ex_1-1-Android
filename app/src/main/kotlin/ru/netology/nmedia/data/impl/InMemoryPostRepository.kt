package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POST_AMOUNT.toLong() + 1L

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(
        List(GENERATED_POST_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Нетология — обучение современным профессиям онлайн",
                content = "Events\nПост №$index",
                published = "22.08.2022",
                views = 100_125,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                likes = it.likes + if (it.likedByMe) -1 else 1,
                likedByMe = !it.likedByMe
            )
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(share = it.share + 1)
        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun edit(postId: Long, postContent: String) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(content = postContent)
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(post.copy(id = ++nextId)) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 1000
    }
}