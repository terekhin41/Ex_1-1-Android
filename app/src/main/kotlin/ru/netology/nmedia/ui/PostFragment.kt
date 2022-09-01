package ru.netology.nmedia.ui

import android.content.Intent
import android.database.SQLException
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.adapter.PostsAdapter.ViewHolder
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.PostFragmentBinding
import java.lang.Exception

class PostFragment : Fragment() {

    private val args by navArgs<PostFragmentArgs>()

    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            val viewHolder = ViewHolder(binding, viewModel)
            val post = getPost()
            if (post == null) findNavController().popBackStack()
            else viewHolder.bind(post)

            viewModel.data.observe(viewLifecycleOwner) {
                val updatedPost = getPost()
                if (updatedPost == null) findNavController().popBackStack()
                else viewHolder.bind(updatedPost)
            }

            viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
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

            setFragmentResultListener(
                requestKey = PostContentFragment.REQUEST_KEY
            ) { requestKey, bundle ->
                if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
                val newPostContent = bundle.getString(
                    PostContentFragment.RESULT_KEY
                ) ?: return@setFragmentResultListener
                viewModel.createPost(newPostContent)
            }

            viewModel.navigateToCreatePost.observe(viewLifecycleOwner) { initialContent ->
                val direction = PostFragmentDirections.toPostContentFragment(initialContent)
                findNavController().navigate(direction)
            }

            viewModel.navigateToPlayVideo.observe(viewLifecycleOwner) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }

            viewModel.navigateToPost.observe(viewLifecycleOwner) {}
        }.root



    private fun getPost(): Post? = viewModel.getPostById(args.postId)
}