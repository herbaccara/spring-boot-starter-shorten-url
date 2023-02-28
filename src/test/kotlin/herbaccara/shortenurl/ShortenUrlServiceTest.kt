package herbaccara.shortenurl

import herbaccara.boot.autoconfigure.shortenurl.ShortenUrlAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(
    classes = [
        ShortenUrlAutoConfiguration::class
    ]
)
@TestPropertySource(locations = ["classpath:application.yml"])
class ShortenUrlServiceTest {

    @Autowired
    lateinit var shortenUrlServices: List<ShortenUrlService>

    @Autowired
    lateinit var naverShortenUrlService: NaverShortenUrlService

    @Autowired
    lateinit var btlyService: BitlyService

    private fun shorten(url: String): String? {
        for (shortenUrlService in shortenUrlServices.shuffled()) {
            println(shortenUrlService::class.java.simpleName)
            val s = runCatching { shortenUrlService.shorten(url) }.getOrNull()
            if (s != null) return s
        }
        return null
    }

    @Test
    fun shortenUrlServices() {
        val shorten = shorten("https://google.com")
        println(shorten)
    }

    @Test
    fun naverShortUrlService() {
        val convert = naverShortenUrlService.shorten("https://google.com")
        println(convert)
    }

    @Test
    fun btlyService() {
        val convert = btlyService.shorten("https://google.com")
        println(convert)
    }
}
