package hollannikas.com

import io.ktor.http.*
import io.ktor.server.testing.*
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
}