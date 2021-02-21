package hollannikas.com

import hollannikas.com.models.Child
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `return empty array if there are no children`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/child").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[]", response.content)
            }
        }
    }

    @Test
    fun `when adding a child, it should be stored`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/child") {
                addHeader("content-type", "application/json")
                addHeader("Accept", "application/json")
                setBody(Json.encodeToJsonElement(Child("1", "Larry", "Mwangi")).toString())
            }.apply {
                assertEquals(HttpStatusCode.Accepted, response.status())
                assertEquals("1", response.content)
            }
            handleRequest(HttpMethod.Get, "/child").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[{\"id\":\"1\",\"firstName\":\"Larry\",\"lastName\":\"Mwangi\"}]",
                    response.content)
            }
        }
    }
}