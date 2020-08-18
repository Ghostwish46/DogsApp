package dev.ghost.dogsapp.model.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("breeds/list/all")
    fun getBreedsAsync(): Deferred<Any>

    @GET("breed/{breedName}/images")
    fun getBreedPhotosAsync(@Path("breedName") breedName: String): Deferred<Any>

    @GET("breed/{breedName}/{subBreedName}/images")
    fun getSubBreedPhotosAsync(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String
    ): Deferred<Any>
}