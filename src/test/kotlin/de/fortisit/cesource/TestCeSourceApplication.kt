package de.fortisit.cesource

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<CeSourceApplication>()
        .with(TestcontainersConfiguration::class)
        .run(*args)
}
