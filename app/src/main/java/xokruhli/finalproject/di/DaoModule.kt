package xokruhli.finalproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xokruhli.finalproject.database.CatsDao
import xokruhli.finalproject.database.CatsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDao(database: CatsDatabase): CatsDao {
        return database.catsDao()
    }

}