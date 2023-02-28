package herbaccara.boot.autoconfigure.shortenurl

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "shorten-url.bitly")
@ConstructorBinding
data class BitlyProperties(
    val enabled: Boolean = true,
    val uri: String = "https://api-ssl.bitly.com/v4/shorten",
    val accessToken: String
)
