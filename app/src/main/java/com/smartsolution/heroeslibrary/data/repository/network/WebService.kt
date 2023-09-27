package com.smartsolution.heroeslibrary.data.repository.network

import com.smartsolution.heroeslibrary.data.repository.core.Constants
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDataResponse
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {
    @GET("/api/${Constants.API_KEY}/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName: String): Response<SuperHeroDataResponse>

    @GET("/api/${Constants.API_KEY}/{id}")
    suspend fun getSuperHeroDetail(@Path("id") superheroId: String): Response<SuperHeroDetailResponse>
}