package ar.com.wolox.android.example.model

data class LoginResponse(
    val data: Data,
    val success: Boolean,
    val errors: List<String>
)

data class Data(
    val id: Long,
    val email: String,
    val password: String,
    val provider: String,
    val uid: String,
    val allowPasswordChange: Boolean,
    val name: String,
    val nickname: String,
    val image: Any? = null
)