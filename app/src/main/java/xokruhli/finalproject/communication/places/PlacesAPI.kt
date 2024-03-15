package xokruhli.finalproject.communication.places

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import xokruhli.finalproject.model.googlePlacesApi.Place

interface PlacesApi {
    @GET("place/nearbysearch/json")
    suspend fun searchNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): Response<Place>
}
