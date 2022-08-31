package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.data.adapter.PostsAdapter
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(
                intent, getString(R.string.chooser_share_post)
            )
            startActivity(shareIntent)
        }

        val postActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent : String? ->
            postContent ?: return@registerForActivityResult
            viewModel.createPost(postContent)
        }

        viewModel.navigateToCreatePost.observe(this) {
            postActivityLauncher.launch(null)
        }

        viewModel.navigateToEditPost.observe(this) {
            postActivityLauncher.launch(it)
        }

        viewModel.navigateToPlayVideo.observe(this) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        }
    }
}