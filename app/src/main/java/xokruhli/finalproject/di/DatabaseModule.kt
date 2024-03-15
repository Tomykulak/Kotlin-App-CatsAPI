package xokruhli.finalproject.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xokruhli.finalproject.database.CatsDatabase
import xokruhli.finalproject.datastore.DataStoreRepositoryImpl
import xokruhli.finalproject.datastore.IDataStoreRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CatsDatabase {
        return CatsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Application): IDataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }
}