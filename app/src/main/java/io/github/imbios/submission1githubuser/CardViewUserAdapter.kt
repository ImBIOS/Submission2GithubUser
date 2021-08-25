package io.github.imbios.submission1githubuser

import User
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.github.imbios.submission1githubuser.databinding.ItemCardviewUserBinding


class CardViewUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<CardViewUserAdapter.CardViewViewHolder>() {

    private var onItemClickCallback: ListUserAdapter.OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: ListUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewUserAdapter.CardViewViewHolder {
        val binding =
            ItemCardviewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewUserAdapter.CardViewViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class CardViewViewHolder(private val binding: ItemCardviewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgItemPhoto)

                tvItemName.text = user.name
                tvItemDescription.text = user.company

                btnSetFavorite.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Favorite ${user.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                btnSetShare.setOnClickListener {
                    Toast.makeText(itemView.context, "Share ${user.name}", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Cool! ${user.name} working in ${user.company}"
                    )
                    intent.type = "text/plain"
                    itemView.context.startActivity(Intent.createChooser(intent, "Send To"))
                }
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih ${user.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}