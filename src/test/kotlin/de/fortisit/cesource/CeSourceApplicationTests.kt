package de.fortisit.cesource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import java.time.Duration

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class CeSourceApplicationTests(
    @Autowired private val kafkaTemplate: KafkaTemplate<String, String>,
    @Autowired private val consumerFactory: ConsumerFactory<String, String>,
) {

    init {
        kafkaTemplate.setConsumerFactory(consumerFactory)
    }

    @Test
    fun `should produce correct ce_source`() {
        val record = kafkaTemplate.receive("test", 0, 0, Duration.ofSeconds(1))

        assertThat(record?.headers()?.headers("ce_source")).allSatisfy {
            assertThat(it.value().decodeToString()).isEqualTo("/my/uri")
        }
    }

    @Test
    fun `should produce correct ce_time`() {
        val record = kafkaTemplate.receive("test", 0, 0, Duration.ofSeconds(1))

        assertThat(record?.headers()?.headers("ce_time")).allSatisfy {
            assertThat(it.value().decodeToString()).isEqualTo("2023-04-03T14:33:19.024807645Z")
        }
    }
}
