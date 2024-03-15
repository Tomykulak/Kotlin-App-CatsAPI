package xokruhli.finalproject.model.catApi

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Image(
    var id: String?,
    var width: Int?,
    var height: Int?,
    var url: String?
): Serializable