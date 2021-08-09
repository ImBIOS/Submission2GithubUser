package io.github.imbios.submission1githubuser

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail User"

        val tvName: TextView = findViewById(R.id.tv_name)
        val tvTagline: TextView = findViewById(R.id.tv_tagline)
        val tvData: TextView = findViewById(R.id.tv_data)
        val imgPhoto: ImageView = findViewById(R.id.img_photo)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        tvName.text = user.name
        tvTagline.text = "@${user.username} | ${user.company}"
        tvData.text = "Location: ${user.location}\nRepository: ${user.repository}\n" +
                "Followers: ${user.followers}\nFollowing: ${user.following}"
        imgPhoto.setImageResource(user.avatar)
    }
}