package xokruhli.finalproject.communication.pets

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.model.catApi.CatImage
import javax.inject.Inject

class PetsRemoteRepositoryImpl
    @Inject constructor(private val petsAPI: PetsAPI) : IPetsRemoteRepository {
    override suspend fun getBreeds(): CommunicationResult<List<Breed>> {
        return processResponse(
            withContext(Dispatchers.IO) {
                petsAPI.getBreeds()
            }
        )
    }

    override suspend fun getBreedById(id: String): CommunicationResult<Breed> {
        return processResponse(
            withContext(Dispatchers.IO) {
                petsAPI.getBreedById(id = id)
            }
        )
    }

    override suspend fun getAllImages(): CommunicationResult<List<CatImage>> {
        return processResponse(
            withContext(Dispatchers.IO) {
                petsAPI.getAllImages()
            }
        )
    }

}