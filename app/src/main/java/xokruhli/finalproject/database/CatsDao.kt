package xokruhli.finalproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import xokruhli.finalproject.model.profile.Cat

@Dao
interface CatsDao {
    @Query("SELECT * FROM cats")
    fun getAll(): Flow<List<Cat>>

    @Insert
    suspend fun insert(cat: Cat)

    @Delete
    suspend fun deleteCat(cat: Cat)

    @Query("SELECT * FROM cats WHERE id = :catId")
    fun getCatById(catId: Long): Flow<Cat>

    @Update
    suspend fun updateCat(cat: Cat)


}