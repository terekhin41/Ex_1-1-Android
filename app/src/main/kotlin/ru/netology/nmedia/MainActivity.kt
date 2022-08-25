package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.data.adapter.PostsAdapter
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard

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
        }
    }
}