package de.fortisit.cesource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder
import org.springframework.cloud.function.cloudevent.CloudEventMessageUtils
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.converter.KafkaMessageHeaders
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.time.OffsetDateTime
import java.util.function.Supplier

@SpringBootApplication
class CeSourceApplication {

    @Bean
    fun cloudEvents(): Supplier<Flux<Message<String>>> = Supplier {
        Flux.just("Hello World")
            .map {
                CloudEventMessageBuilder
                    .withData(it)
                    .setHeader(KafkaHeaders.KEY, "foo")
                    .setSource("/my/uri")
                    .setTime(OffsetDateTime.parse("2023-04-03T14:33:19.024807645Z"))
                    .build(CloudEventMessageUtils.KAFKA_ATTR_PREFIX)
            }
    }
}

fun main(args: Array<String>) {
    runApplication<CeSourceApplication>(*args)
}
