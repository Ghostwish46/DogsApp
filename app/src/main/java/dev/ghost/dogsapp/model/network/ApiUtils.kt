package dev.ghost.dogsapp.model.network

object ApiUtils {

    private const val BASE_URL = "https://dog.ceo/api/"

    val apiService: ApiService
        get() = RetrofitClient.getClient(
            BASE_URL
        )!!.create(ApiService::class.java)
}