package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(
        List(1000) { index ->
            Post(
                id = index + 1L,
                author = "Нетология — обучение современным профессиям онлайн",
                content = "Events\nПост №$index",
                published = "22.08.2022"
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
}