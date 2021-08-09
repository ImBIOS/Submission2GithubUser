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

        val tvObject: TextView = findViewById(R.id.tv_object_received)
        val imgPhoto: ImageView = findViewById(R.id.img_photo)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val text = "Name : ${user.name.toString()},\nCompany : ${user.company},\nUsername : ${user.username},\nLocation : ${user.location}"
        tvObject.text = text
        imgPhoto.setImageResource(user.avatar)
    }
}