package io.github.imbios.submission1githubuser

import User
import UserAdapter
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var dataUsername: Array<String>
    private lateinit var dataRepository: TypedArray
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowers: TypedArray
    private lateinit var dataFollowing: TypedArray
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Github User's"

        val listView: ListView = findViewById(R.id.lv_list)
        adapter = UserAdapter(this)
        listView.adapter = adapter

        prepare()
        addItem()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val user = User(
                users[position].avatar,
                users[position].name,
                users[position].location,
                users[position].username,
                users[position].repository,
                users[position].company,
                users[position].followers,
                users[position].following
            )

            val moveWithObjectIntent = Intent(this@MainActivity, DetailActivity::class.java)
            moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, user)
            startActivity(moveWithObjectIntent)
        }
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.name)
        dataDescription = resources.getStringArray(R.array.location)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataUsername = resources.getStringArray(R.array.username)
        dataRepository = resources.obtainTypedArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowers = resources.obtainTypedArray(R.array.followers)
        dataFollowing = resources.obtainTypedArray(R.array.following)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataPhoto.getResourceId(position, -1),
                dataName[position],
                dataDescription[position],
                dataUsername[position],
                dataRepository.getResourceId(position, -1),
                dataCompany[position],
                dataFollowers.getResourceId(position, -1),
                dataFollowing.getResourceId(position, -1),
            )
            users.add(user)
        }
        adapter.users = users
    }
}