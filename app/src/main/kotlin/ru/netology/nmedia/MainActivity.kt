package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Нетология. Меняем карьеру через образование",
            content = "Events",
            published = "16.08.2022"
        )
        binding.render(post)

        binding.postLikesImage.setOnClickListener {
            post.like()
            binding.render(post)
        }

        binding.postShareImage.setOnClickListener {
            post.share()
            binding.render(post)
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        content.text = post.content
        published.text = post.published
        likesCount.text = post.likesToString()
        shareCount.text = post.shareToString()
        viewsCount.text = post.viewsToString()
        postLikesImage.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
}