package com.example.latihanrestapi.network

import com.example.latihanrestapi.data.User
import com.example.latihanrestapi.data.Post
import com.example.latihanrestapi.data.PostBaru

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

// ... (isi interface ApiService tetap sama)
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}/posts")
    suspend fun getPostsByUser(@Path("id") userId: Int): List<Post>

    @POST("posts")
    suspend fun buatPost(@Body post: PostBaru): Post
}