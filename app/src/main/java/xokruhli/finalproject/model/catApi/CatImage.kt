package xokruhli.finalproject.model.catApi

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CatImage(
    var id: String?,
    var url: String?,
    var breeds: List<Breed>?,
    var width: Int?,
    var height: Int?
): Serializable

