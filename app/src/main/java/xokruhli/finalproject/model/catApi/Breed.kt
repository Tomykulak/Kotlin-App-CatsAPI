package xokruhli.finalproject.model.catApi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Breed(
    var weight: Weight?,
    var id: String?,
    var name: String?,
    @Json(name = "cfa_url")
    var cfaUrl: String?,
    @Json(name = "vetstreet_url")
    var vetstreetUrl: String?,
    @Json(name = "vcahospitals_url")
    var vcahospitalsUrl: String?,
    var temperament: String?,
    var origin: String?,
    @Json(name = "country_codes")
    var countryCodes: String?,
    @Json(name = "country_code")
    var countryCode: String?,
    var description: String?,
    @Json(name = "life_span")
    var lifeSpan: String?,
    var indoor: Int?,
    var lap: Int?,
    @Json(name = "alt_names")
    var altNames: String?,
    var adaptability: Int?,
    @Json(name = "affection_level")
    var affectionLevel: Int?,
    @Json(name = "child_friendly")
    var childFriendly: Int?,
    @Json(name = "dog_friendly")
    var dogFriendly: Int?,
    @Json(name = "energy_level")
    var energyLevel: Int?,
    var grooming: Int?,
    @Json(name = "health_issues")
    var healthIssues: Int?,
    var intelligence: Int?,
    @Json(name = "shedding_level")
    var sheddingLevel: Int?,
    @Json(name = "social_needs")
    var socialNeeds: Int?,
    @Json(name = "stranger_friendly")
    var strangerFriendly: Int?,
    var vocalisation: Int?,
    var experimental: Int?,
    var hairless: Int?,
    var natural: Int?,
    var rare: Int?,
    var rex: Int?,
    @Json(name = "suppressed_tail")
    var suppressedTail: Int?,
    @Json(name = "short_legs")
    var shortLegs: Int?,
    @Json(name = "wikipedia_url")
    var wikipediaUrl: String?,
    var hypoallergenic: Int?,
    @Json(name = "reference_image_id")
    var referenceImageId: String?,
    var image: Image?
): Serializable