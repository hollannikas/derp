package hollannikas.com.models

import kotlinx.serialization.Serializable

@Serializable
data class Child(val id: String, val firstName: String, val lastName: String)

val database = mutableListOf<Child>()
