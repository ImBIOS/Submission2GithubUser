package io.github.imbios.submission1githubuser

import User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.imbios.submission1githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_user)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        binding.tvName.text = user.name
        binding.tvTagline.text = "@${user.username} | ${user.company}"
        binding.tvData.text = "Location: ${user.location}\nRepository: ${user.repository}\n" +
                "Followers: ${user.followers}\nFollowing: ${user.following}"
        binding.imgPhoto.setImageResource(user.avatar)
    }
}