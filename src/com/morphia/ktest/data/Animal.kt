package com.morphia.ktest.data

import dev.morphia.annotations.*
import org.bson.types.ObjectId

@Entity
class Animal {
    @Id
    var id: ObjectId? = null
    var name: String? = null
    var age: String? = null

    override fun toString(): String {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\''.toString() +
                ", age='" + age + '\''.toString() +
                '}'.toString()
    }
}

@Entity("users")
@Indexes(
    Index(value = "userId", fields = [Field("userId")], options = IndexOptions(unique = true))
)
data class User(
    val userId: String,
    val name: String,
    val phone: String,
    @Id
    var _id: String? = phone
) {
    constructor() : this("", "", "")
}