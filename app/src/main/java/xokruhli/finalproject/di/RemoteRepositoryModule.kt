package cz.mendelu.pef.petstore.di

import xokruhli.finalproject.communication.pets.PetsAPI
import xokruhli.finalproject.communication.pets.PetsRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xokruhli.finalproject.communication.places.IPlacesRemoteRepository
import xokruhli.finalproject.communication.places.PlaceRemoteRepositoryImpl
import xokruhli.finalproject.communication.places.PlacesApi
import xokruhli.finalproject.database.CatsDao
import xokruhli.finalproject.database.CatsRepositoryImpl
import xokruhli.finalproject.database.ICatsRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {
    @Provides
    @Singleton
    fun providePetsRepository(api: PetsAPI): PetsRemoteRepositoryImpl
        = PetsRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePlacesRepository(api: PlacesApi): IPlacesRemoteRepository = PlaceRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideLocalRepository(dao: CatsDao): ICatsRepository {
        return CatsRepositoryImpl(dao)
    }
}