package io.github.imbios.submission2githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import io.github.imbios.submission2githubuser.databinding.FragmentFollowingBinding
import io.github.imbios.submission2githubuser.databinding.ItemCardviewUserBinding

var FollowingFilterList = ArrayList<UserData>()

class FollowingAdapter(listUser: ArrayList<UserData>) :
    RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    init {
        FollowingFilterList = listUser
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemCardviewUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = FollowingFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.binding.imgItemPhoto)
        holder.binding.txtUsername.text = data.username
        holder.binding.txtName.text = data.name
        holder.binding.txtCompany.text = data.company
        holder.binding.txtLocation.text = data.location
        holder.itemView.setOnClickListener {
            //DO NOTHING
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(UserData: UserData)
    }

    override fun getItemCount(): Int = FollowingFilterList.size

    inner class ListViewHolder(var binding: ItemCardviewUserBinding) : RecyclerView.ViewHolder(binding.root)

}