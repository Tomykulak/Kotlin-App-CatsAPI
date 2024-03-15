package xokruhli.finalproject.model.googlePlacesApi

data class Place(
    val html_attributions: List<Any>,
    val results: List<Result>,
    val status: String
)