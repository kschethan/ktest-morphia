package com.morphia.ktest

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.morphia.ktest.data.Animal
import com.morphia.ktest.data.User
import dev.morphia.Morphia
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun Application.main() {

    install(CallLogging)
    install(ContentNegotiation) {
        gson()
    }

    val morphia = Morphia()
    morphia.mapPackage("com.morphia.ktest.data")
    val datastore = morphia.createDatastore(
        MongoClient(MongoClientURI("mongodb://test:test123@127.0.0.1:27017/test_db")),
        "test_db"
    )

    routing {
        get("/") {
            println("Hello world")
            call.respond(HttpStatusCode.OK)
        }

        get("/animal") {
            val animal = Animal()
            animal.name = "Tiger"
            animal.age = "2"

            datastore.save(animal)

            println("Here is the animal: " + datastore.createQuery(Animal::class.java).field("name").equalIgnoreCase("Tiger").first())
            call.respond(HttpStatusCode.OK)
        }

        get("/user") {
            val user = User(
                userId = "hwrmhmi7z053h",
                name = "Jon Doe",
                phone = "999999999",
                _id = "999999999"
            )
            println("Connected to Database: " + datastore.database)

            datastore.save(user)

            println("Here is the animal: " + datastore.createQuery(User::class.java).field("phone").equalIgnoreCase("999999999").first())
            call.respond(HttpStatusCode.OK)
        }
    }
}
