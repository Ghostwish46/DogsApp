package dev.ghost.dogsapp.helpers

import dev.ghost.dogsapp.response.ResponseBreeds
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

//
//    @GET("priorities")
//    @Headers("Accept:application/json")
//    fun getPrioritiesAsync(@Query("api_token") token: String): Deferred<List<Priority>>


}