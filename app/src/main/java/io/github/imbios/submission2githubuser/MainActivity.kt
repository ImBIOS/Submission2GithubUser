package io.github.imbios.submission2githubuser

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.github.imbios.submission2githubuser.databinding.ActivityMainBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerCardView()
        supportActionBar?.title = getString(R.string.github_user)

        recyclerViewConfig()
        searchUser()
        getUser()
    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getUser("ImBIOS")
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        setRestaurantData(responseBody.)
//                        setReviewData(responseBody.restaurant.customerReviews)
                        val item = responseBody.items
                        for (i in item?.indices!!) {
                            val jsonObject = item[i]
                            val username: String = jsonObject?.login!!
                            getDetailActivity(username)
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    viewModel.users.clear()
                    getUserSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

//    private fun getUser() {
//        binding.progressBar.visibility = View.VISIBLE
//        val client = AsyncHttpClient()
//        client.addHeader("User-Agent", "ImBIOS/MyGithubUserApp")
//        client.addHeader("Authorization", "token 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")
//
//        val url = "https://api.github.com/search/users?q=ImBIOS"
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<Header>,
//                responseBody: ByteArray
//            ) {
//                binding.progressBar.visibility = View.INVISIBLE
//                val result = String(responseBody)
//                Log.d(TAG, result)
//                try {
//                    val jsonArray = JSONObject(result)
//                    val item = jsonArray.getJSONArray("items")
//                    for (i in 0 until item.length()) {
//                        val jsonObject = item.getJSONObject(i)
//                        val username: String = jsonObject.getString("login")
//                        getDetailActivity(username)
//                    }
//                } catch (e: Exception) {
//                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
//                        .show()
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<Header>,
//                responseBody: ByteArray,
//                error: Throwable
//            ) {
//                binding.progressBar.visibility = View.INVISIBLE
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error.message}"
//                }
//                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
//                    .show()
//            }
//        })
//    }

    private fun getUserSearch(id: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "ImBIOS/MyGithubUserApp")
        client.addHeader("Authorization", "token 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")

        val url = "https://api.github.com/search/users?q=$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailActivity(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun getDetailActivity(id: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "ImBIOS/MyGithubUserApp")
        client.addHeader("Authorization", "token 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")

        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")
                    viewModel.users.add(
                        UserData(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    showRecyclerCardView()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun recyclerViewConfig() {
        binding.rvUsers.layoutManager = LinearLayoutManager(binding.rvUsers.context)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.addItemDecoration(
            DividerItemDecoration(
                binding.rvUsers.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun showRecyclerCardView() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val cardViewUserAdapter = CardViewUserAdapter(viewModel.users)
        binding.rvUsers.adapter = cardViewUserAdapter

        cardViewUserAdapter.setOnItemClickCallback(object :
            CardViewUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(dataUser: UserData) {
        UserData(
            dataUser.username,
            dataUser.name,
            dataUser.avatar,
            dataUser.company,
            dataUser.location,
            dataUser.repository,
            dataUser.followers,
            dataUser.following
        )
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, dataUser)

        this@MainActivity.startActivity(intent)
        Toast.makeText(
            this,
            "${dataUser.name}",
            Toast.LENGTH_SHORT
        ).show()

    }
}