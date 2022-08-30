package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.adapter.PostInteractionListener
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToCreatePost = SingleLiveEvent<Unit>()
    val navigateToEditPost = SingleLiveEvent<String>()
    val navigateToPlayVideo = SingleLiveEvent<String>()
    private var currentPost : Post? = null

    fun createPost(content: String) {
        if (content.isBlank()) return
        val post = currentPost?.copy(
            content = content
        ) ?: Post(
                id = PostRepository.NEW_POST_ID,
                author = "Нетология — обучение современным профессиям онлайн",
                content = content,
                published = "Today"
            )
        repository.save(post)
        currentPost = null
    }

    override fun onAddClicked() {
        navigateToCreatePost.call()
    }
    override fun onLikeClicked(post: Post) {
        repository.like(post.id)
    }
    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
    }
    override fun onRemoveClicked(post: Post) {
        repository.delete(post.id)
    }
    override fun onEditClicked(post: Post) {
        currentPost = post
        navigateToEditPost.value = post.content
    }

    override fun onPlayClicked(post: Post) {
        val url : String = if (post.videoUrl != null) post.videoUrl!! else return
        navigateToPlayVideo.value = url
    }
}