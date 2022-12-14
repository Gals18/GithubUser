package com.galuhsaputri.favorite.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galuhsaputri.githubusers.ui.detail.UserDetailActivity
import com.galuhsaputri.core.utils.viewUtils.load
import com.galuhsaputri.core.domain.model.UserFavorite
import com.galuhsaputri.favorite.R
import com.galuhsaputri.favorite.databinding.ItemRowFavoriteUserBinding


class FavoriteUserAdapter(private val mContext: Context) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private var items: MutableList<UserFavorite> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemRowFavoriteUserBinding = ItemRowFavoriteUserBinding.bind(itemView)

        fun bind(userEntity: UserFavorite) {
            with(itemView) {
                binding.apply {
                    txtUsername.text = userEntity.username
                    txtLocation.text =
                        userEntity.location ?: context.getString(R.string.no_location)
                    txtFollower.text = userEntity.followers.toString()
                    txtFollowing.text = userEntity.following.toString()
                    txtRepository.text = userEntity.publicRepos.toString()
                    txtCompany.text = userEntity.company ?: context.getString(R.string.no_company)
                    binding.ivUser.load(userEntity.avatarUrl)
                }
                itemView.setOnClickListener {
                    context.startActivity(
                        Intent(
                            context,
                            UserDetailActivity::class.java
                        ).apply {
                            putExtra(UserDetailActivity.USERNAME_KEY, userEntity.username)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                }
            }

        }
    }

    fun setItems(items: MutableList<UserFavorite>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_row_favorite_user, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}