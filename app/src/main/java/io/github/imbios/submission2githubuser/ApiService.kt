package io.github.imbios.submission2githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
            "User-Agent: ImBIOS/MyGithubUserApp",
            "Authorization: token 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"
    )
    @GET("search/users")
    fun getUser(
        @Query("q") id: String
    ): Call<UserResponse>
}