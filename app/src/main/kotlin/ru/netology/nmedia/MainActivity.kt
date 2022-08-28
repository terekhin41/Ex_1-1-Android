package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.data.adapter.PostsAdapter
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var requestLauncher: ActivityResultLauncher<Unit>

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
        /*
        binding.saveButton.setOnClickListener {
            with (binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
            }
        }

        binding.editCloseImage.setOnClickListener {
            viewModel.onCloseEdit()
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with (binding) {
                content.setText(currentPost?.content)
                if (currentPost != null) {
                    editPostAuthor.text = currentPost.author
                    editContent.text = currentPost.content
                    editInfoLayout.visibility = View.VISIBLE
                } else {
                    editInfoLayout.visibility = View.GONE
                    editPostAuthor.text = ""
                    editContent.text = ""
                }
            }
        }*/

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

        val createPostActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent : String? ->
            postContent ?: return@registerForActivityResult
            viewModel.createPost(postContent)
        }

        viewModel.navigateToCreatePost.observe(this) {
            createPostActivityLauncher.launch()
        }

        viewModel.navigateToPlayVideo.observe(this) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        }

        /*viewModel.navigateToEditPost.observe(this) {
            val contract = PostContentActivity.ResultContract(postContent = it.content)
            val postContentActivityLauncher = registerForActivityResult(
                contract
            ) { postContent : String? ->
                postContent ?: return@registerForActivityResult
                viewModel.editPost(it.id, postContent)
            }
            postContentActivityLauncher.launch()
        }*/
    }

    /*override fun onResume() {
        super.onResume()
        viewModel.currentPost.value = null
    }*/

    /*fun editPostActivityLauncher(post: Post) {
        registerForActivityResult(
            PostContentActivity.ResultContract(post.content)
        ) { postContent: String? ->
            postContent ?: return@registerForActivityResult
            viewModel.editPost(
                postId = post.id,
                postContent = post.content
            )
        }.launch()
    }*/
}