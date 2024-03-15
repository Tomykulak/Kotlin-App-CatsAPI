package xokruhli.finalproject.database

import kotlinx.coroutines.flow.Flow
import xokruhli.finalproject.model.profile.Cat

class CatsRepositoryImpl (private val catsDao: CatsDao) : ICatsRepository {

    override fun getAll(): Flow<List<Cat>> {
        return catsDao.getAll()
    }

    override suspend fun insert(cat: Cat) {
        catsDao.insert(cat)
    }
    override suspend fun deleteCat(cat: Cat) {
        catsDao.deleteCat(cat)
    }

    override fun getCatById(catId: Long): Flow<Cat> {
        return catsDao.getCatById(catId)
    }

    override suspend fun updateCatById(cat: Cat) {
        return catsDao.updateCat(cat)
    }


}
