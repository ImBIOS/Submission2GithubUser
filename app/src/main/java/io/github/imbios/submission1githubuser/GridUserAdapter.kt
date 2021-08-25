package io.github.imbios.submission1githubuser

import User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.github.imbios.submission1githubuser.databinding.ItemGridUserBinding

class GridUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<GridUserAdapter.GridViewHolder>() {

    private var onItemClickCallback: ListUserAdapter.OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: ListUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): GridUserAdapter.GridViewHolder {
        val binding =
            ItemGridUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridUserAdapter.GridViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class GridViewHolder(private val binding: ItemGridUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgItemPhoto)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}