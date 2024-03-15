package xokruhli.finalproject.datastore

interface IDataStoreRepository {
    suspend fun saveImageState(url: String, downloaded: Boolean)
    suspend fun isImageDownloaded(url: String): Boolean
}