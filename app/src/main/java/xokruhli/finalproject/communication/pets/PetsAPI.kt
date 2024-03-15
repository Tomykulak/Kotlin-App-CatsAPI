package xokruhli.finalproject.communication.pets

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.model.catApi.CatImage

interface PetsAPI {
    @Headers("x-api-key: live_8CJ6q0rzJe3VVwM82urS6hapMIqKbItwNyEyzxj2b2Atxr3odvNSHjYJZFnRCizi")
    @GET("breeds")
    suspend fun getBreeds(): Response<List<Breed>>

    @GET("breeds/{id}")
    suspend fun getBreedById(@Path("id") id: String): Response<Breed>

    @Headers("x-api-key: live_8CJ6q0rzJe3VVwM82urS6hapMIqKbItwNyEyzxj2b2Atxr3odvNSHjYJZFnRCizi")
    @GET("images")
    suspend fun getAllImages(): Response<List<CatImage>>
}