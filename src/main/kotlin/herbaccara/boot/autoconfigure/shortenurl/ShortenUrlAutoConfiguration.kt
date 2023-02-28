package herbaccara.boot.autoconfigure.shortenurl

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import herbaccara.shortenurl.BitlyService
import herbaccara.shortenurl.NaverShortenUrlService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets
import java.util.*

@AutoConfiguration
class ShortenUrlAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper::class)
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            findAndRegisterModules()
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate::class)
    fun restTemplate(objectMapper: ObjectMapper): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(
                StringHttpMessageConverter(StandardCharsets.UTF_8),
                AllEncompassingFormHttpMessageConverter(),
                MappingJackson2HttpMessageConverter(objectMapper)
            )
            .build()
    }

    @AutoConfiguration
    @EnableConfigurationProperties(NaverShortenUrlProperties::class)
    @ConditionalOnProperty(prefix = "shorten-url.naver", value = ["enabled"], havingValue = "true")
    class NaverShortenUrlAutoConfiguration {

        @Bean
        fun naverShortUrlService(
            restTemplate: RestTemplate,
            objectMapper: ObjectMapper,
            properties: NaverShortenUrlProperties
        ): NaverShortenUrlService {
            if (properties.clientId.isEmpty()) throw NullPointerException()
            if (properties.clientSecret.isEmpty()) throw NullPointerException()

            return NaverShortenUrlService(restTemplate, objectMapper, properties)
        }
    }

    @AutoConfiguration
    @EnableConfigurationProperties(BitlyProperties::class)
    @ConditionalOnProperty(prefix = "shorten-url.bitly", value = ["enabled"], havingValue = "true")
    class BitlyAutoConfiguration {

        @Bean
        fun bitlyService(
            restTemplate: RestTemplate,
            objectMapper: ObjectMapper,
            properties: BitlyProperties
        ): BitlyService {
            if (properties.accessToken.isEmpty()) throw NullPointerException()

            return BitlyService(restTemplate, objectMapper, properties)
        }
    }
}
