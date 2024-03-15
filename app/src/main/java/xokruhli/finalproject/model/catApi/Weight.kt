package xokruhli.finalproject.model.catApi

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Weight(
    var imperial: String?,
    var metric: String?
): Serializable