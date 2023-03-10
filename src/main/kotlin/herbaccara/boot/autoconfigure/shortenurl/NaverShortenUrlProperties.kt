package herbaccara.boot.autoconfigure.shortenurl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "shorten-url.naver")
@ConstructorBinding
data class NaverShortenUrlProperties(
    val enabled: Boolean = true,
    val uri: String = "https://openapi.naver.com/v1/util/shorturl",
    val clientId: String,
    val clientSecret: String
)
