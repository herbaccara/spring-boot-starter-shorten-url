package herbaccara.shortenurl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import herbaccara.boot.autoconfigure.shortenurl.BitlyProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

class BitlyService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val properties: BitlyProperties
) : ShortUrlService {

    override fun shorten(url: String): String {
        val uri = "https://api-ssl.bitly.com/v4/shorten"

        val headers = HttpHeaders().apply {
            setBearerAuth(properties.accessToken)
            contentType = MediaType.APPLICATION_JSON
        }

        val form = mapOf("long_url" to url)

        val httpEntity = HttpEntity<Map<String, String>>(form, headers)

        val json: JsonNode = restTemplate.postForObject(uri, httpEntity)

        /*
        {
            "created_at" : "2023-02-28T05:46:46+0000",
            "id" : "bit.ly/3mfYtda",
            "link" : "https://bit.ly/3mfYtda",
            "custom_bitlinks" : [],
            "long_url" : "https://google.com/",
            "archived" : false,
            "tags" : [],
            "deeplinks" : [],
            "references" : {
                "group" : "https://api-ssl.bitly.com/v4/groups/Bn2s4kxkqie"
            }
        }
        */

        return json["link"].asText()
    }
}
