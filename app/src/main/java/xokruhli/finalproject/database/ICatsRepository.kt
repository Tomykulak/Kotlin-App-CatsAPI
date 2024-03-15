package xokruhli.finalproject.database

import kotlinx.coroutines.flow.Flow
import xokruhli.finalproject.model.profile.Cat

interface ICatsRepository {
    fun getAll(): Flow<List<Cat>>
    suspend fun insert(cat: Cat)
    suspend fun deleteCat(cat: Cat)

    fun getCatById(catId: Long): Flow<Cat>
    suspend fun updateCatById(cat: Cat)
}