package com.example.module.network.net

import retrofit2.http.GET

interface NetworkApi {
    @GET("project/tree/json")
    suspend fun loadProjectTree() : BaseModel<List<ProjectTree>>
}