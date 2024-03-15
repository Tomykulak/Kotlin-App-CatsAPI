package cz.mendelu.pef.petstore.di

import xokruhli.finalproject.communication.pets.PetsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import xokruhli.finalproject.communication.places.PlacesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun providePetsAPI(retrofit: Retrofit): PetsAPI
    = retrofit.create(PetsAPI::class.java)

    @Provides
    @Singleton
    fun provideGooglePlacesApi(@Named("provideRetrofitGoogle") retrofit: Retrofit): PlacesApi
            = retrofit.create(PlacesApi::class.java)

}