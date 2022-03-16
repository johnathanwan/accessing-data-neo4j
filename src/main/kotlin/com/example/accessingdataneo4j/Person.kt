package com.example.accessingdataneo4j

import org.springframework.data.neo4j.core.schema.*

@Suppress("unused")
@Node
class Person(
    var name: String,
    @Id
    @GeneratedValue
    private var id: Long? = null

) {

    @Relationship(type = "TEAMMATE")
    var teammates: MutableSet<Person>? = null

    fun worksWith(person: Person) = teammates?.add(person) ?: run { teammates = HashSet() }

    override fun toString(): String = "$name's teammates => ${teammates?.map { it.name }?.toList() ?: emptyList()}"

}