package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked(post: Post) = repository.like(postId = post.id)
    fun onShareClicked(post: Post) = repository.share(postId = post.id)
}