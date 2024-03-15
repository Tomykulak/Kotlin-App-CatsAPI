package xokruhli.finalproject.communication.places

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.model.googlePlacesApi.Place
import javax.inject.Inject
class PlaceRemoteRepositoryImpl @Inject constructor(
    private val api: PlacesApi
) : IPlacesRemoteRepository {
    override suspend fun searchNearbyVeterinary(location: String, radius: Int, apiKey: String): CommunicationResult<Place> {
        return processResponse(
            withContext(Dispatchers.IO) {
                api.searchNearbyPlaces(location, radius, "veterinary_care", apiKey)
            }
        )
    }
}
