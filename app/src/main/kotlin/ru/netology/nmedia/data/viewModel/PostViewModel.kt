package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.adapter.PostInteractionListener
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isNotBlank()) {
            val post = currentPost.value?.copy(
                content = content
            ) ?: Post(
                id = PostRepository.NEW_POST_ID,
                author = "Нетология — обучение современным профессиям онлайн",
                content = content,
                published = "Today"
            )
            repository.save(post)
        }
        currentPost.value = null
    }
    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) = repository.share(post.id)
    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) { currentPost.value = post }
    override fun onCloseEdit() { currentPost.value = null }
}