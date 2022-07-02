package com.example.desafioandroid.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.desafioandroid.R
import com.example.desafioandroid.data.model.RepoModel
import com.example.desafioandroid.databinding.FragmentRepoBinding
import javax.inject.Inject


class RepoAdapter @Inject constructor() :
    ListAdapter<RepoModel, RepoAdapter.RepoViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<RepoModel>() {
        override fun areItemsTheSame(
            oldItem: RepoModel,
            newItem: RepoModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: RepoModel,
            newItem: RepoModel
        ): Boolean {
            return oldItem.idRepo === newItem.idRepo
        }
    }

    private var onItemClickListener: ((RepoModel) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (RepoModel) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = FragmentRepoBinding.inflate(LayoutInflater.from(parent.context))
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(repoViewHolder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        repoViewHolder.bind(repo)
    }

    inner class RepoViewHolder(private val binding: FragmentRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: RepoModel) {
            binding.ivAvatarUser.load(repo.owner_repos.avatarUrl) {
                crossfade(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .transformations(CircleCropTransformation())
            }

            binding.tvRepoName.text = repo.nameRepo
            binding.tvOwnerFullName.text = repo.fullNameRepo
            binding.tvStar.text = repo.stars.toString()
            binding.tvForks.text = repo.forks.toString()
            binding.tvRepoDescription.text = repo.descriptionRepo
            binding.tvOwnerName.text = repo.owner_repos.loginOwner
            binding.layoutRepo.setOnClickListener {
                onItemClickListener?.invoke(repo)
            }

            itemView.setOnClickListener{
                Toast.makeText(binding.layoutRepo.context, repo.nameRepo, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
