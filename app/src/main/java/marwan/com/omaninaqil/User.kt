package marwan.com.omaninaqil

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name: String? = "",
    var email: String? = "",
    var phone: String? = ""
)