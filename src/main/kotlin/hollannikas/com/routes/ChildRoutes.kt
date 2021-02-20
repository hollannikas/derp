package hollannikas.com.routes

import hollannikas.com.models.Child
import hollannikas.com.models.database
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.childRouting() {
    route("/child") {
        get {
            if (database.isNotEmpty()) {
                call.respond(database)
            } else {
                call.respondText("No children found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                database.find { it.id == id } ?: return@get call.respondText(
                    "No child with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Child>()
            // TODO - This shouldn't really be done in production as
            // we should be accessing a mutable list in a thread-safe manner.
            // However, in production code we wouldn't be using mutable lists as a database!
            database.add(customer)
            call.respondText("Child stored correctly", status = HttpStatusCode.Accepted)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (database.removeIf { it.id == id }) {
                call.respondText("Child removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}


fun Application.registerChildRoutes() {
    routing {
        childRouting()
    }
}