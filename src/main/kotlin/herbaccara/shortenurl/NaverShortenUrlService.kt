package herbaccara.shortenurl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import herbaccara.boot.autoconfigure.shortenurl.NaverShortenUrlProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

class NaverShortenUrlService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val properties: NaverShortenUrlProperties
) : ShortenUrlService {

    override fun shorten(url: String): String {
        val headers = HttpHeaders().apply {
            add("X-Naver-Client-Id", properties.clientId)
            add("X-Naver-Client-Secret", properties.clientSecret)
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val form = LinkedMultiValueMap<String, String>().apply {
            add("url", url)
        }

        val httpEntity = HttpEntity<MultiValueMap<String, String>>(form, headers)

        val json: JsonNode = restTemplate.postForObject(properties.uri, httpEntity)

        /*
        {
            "result" : {
                "url" : "https://me2.do/FFiymZkF",
                "hash" : "FFiymZkF",
                "orgUrl" : "https://test.com/test/1234"
            },
            "message" : "ok",
            "code" : "200"
        }
        */

        return json["result"]["url"].asText()
    }
}
