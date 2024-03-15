package xokruhli.finalproject.communication.pets

import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.architecture.IBaseRemoteRepository
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.model.catApi.CatImage

interface IPetsRemoteRepository : IBaseRemoteRepository {
    suspend fun getBreeds(

    ): CommunicationResult<List<Breed>>

    suspend fun getBreedById(
        id: String
    ): CommunicationResult<Breed>

    suspend fun getAllImages(

    ): CommunicationResult<List<CatImage>>
}