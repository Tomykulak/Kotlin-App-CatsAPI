package xokruhli.finalproject.communication.places


import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.architecture.IBaseRemoteRepository
import xokruhli.finalproject.model.googlePlacesApi.Place

interface IPlacesRemoteRepository : IBaseRemoteRepository {
    suspend fun searchNearbyVeterinary(location: String, radius: Int, apiKey: String): CommunicationResult<Place>
}