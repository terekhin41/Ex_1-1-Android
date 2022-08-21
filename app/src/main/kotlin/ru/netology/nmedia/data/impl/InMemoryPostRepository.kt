package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData<Post>(
        Post(
            id = 0L,
            author = "Нетология. Меняем карьеру через образование",
            content = "Events",
            published = "16.08.2022"
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val likedPost = with (currentPost) {
            copy(
                likes = likes + if (likedByMe) -1 else 1,
                likedByMe = !likedByMe
            )
        }
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val sharedPost = currentPost.copy(share = currentPost.share + 1)
        data.value = sharedPost
    }
}