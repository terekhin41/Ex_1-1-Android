package ru.netology.nmedia.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.postLikesButton.setOnClickListener { listener.onLikeClicked(post) }
            binding.postShareButton.setOnClickListener { listener.onShareClicked(post) }
            binding.videoContentLayout.setOnClickListener { listener.onPlayClicked(post) }
            binding.imagePlayVideo.setOnClickListener { listener.onPlayClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                author.text = post.author
                postContent.text = post.content
                published.text = post.published
                postLikesButton.text = post.likesToString()
                postLikesButton.isChecked = post.likedByMe
                postShareButton.text = post.shareToString()
                viewsCount.text = post.viewsToString()
                if (post.videoUrl != null) {
                    videoTitle.text = post.videoUrl
                    videoContentLayout.visibility = View.VISIBLE
                } else
                    videoContentLayout.visibility = View.GONE
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}