package ru.netology.nmedia.data.adapter

import ru.netology.nmedia.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onAddClicked()
    fun onPlayClicked(post: Post)
    fun onPostClicked(post: Post)
}