package com.example.accessingdataneo4j

import mu.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import kotlin.system.*

@SpringBootApplication
@EnableNeo4jRepositories
class AccessingDataNeo4jApplication {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @Bean
    fun demo(personRepository: PersonRepository): CommandLineRunner {
        return CommandLineRunner {

            personRepository.deleteAll()

            var greg = Person("Greg")
            var roy = Person("Roy")
            var craig = Person("Craig")

            val team = listOf(greg, roy, craig)

            logger.info { "Before linking up with Neo4j..." }

            team.forEach { logger.info { it } }

            personRepository.save(greg)
            personRepository.save(roy)
            personRepository.save(craig)

            greg = personRepository.findByName(greg.name)
            greg.worksWith(roy)
            greg.worksWith(craig)
            personRepository.save(greg)

            roy = personRepository.findByName(roy.name)
            roy.worksWith(craig)
            personRepository.save(roy)

            logger.info { "Lookup each person by name..." }

            team.forEach {
                logger.info { personRepository.findByName(it.name) }
            }

            val teammates = personRepository.findByTeammatesName(roy.name)
            logger.info { "The following have Greg as a teammate..." }
            teammates.forEach { logger.info { it.name } }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AccessingDataNeo4jApplication>(*args)
    exitProcess(0)
}
