package org.karigor.friends.dto

data class Location(
    var street: Street? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null,
    var coordinates: Coordinates? = null,
    var timezone: Timezone? = null
)