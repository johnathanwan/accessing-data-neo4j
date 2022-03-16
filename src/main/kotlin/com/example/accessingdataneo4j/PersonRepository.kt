package com.example.accessingdataneo4j

import org.springframework.data.neo4j.repository.*

@Suppress("unused")
interface PersonRepository : Neo4jRepository<Person, Long> {
    fun findByName(name: String): Person
    fun findByTeammatesName(name: String): List<Person>

}