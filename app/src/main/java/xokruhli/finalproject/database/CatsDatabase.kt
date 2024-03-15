package xokruhli.finalproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xokruhli.finalproject.model.profile.Cat


@Database(entities = [Cat::class], version = 6, exportSchema = false)
abstract class CatsDatabase : RoomDatabase() {

    abstract fun catsDao(): CatsDao

    companion object {

        private var INSTANCE: CatsDatabase? = null

        fun getDatabase(context: Context): CatsDatabase {
            if (INSTANCE == null) {
                synchronized(CatsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CatsDatabase::class.java, "cats_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
