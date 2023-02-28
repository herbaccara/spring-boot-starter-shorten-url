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

    @Test
    fun naverShortUrlService() {
        val convert = naverShortenUrlService.shorten("https://google.com")
        println()
    }

    @Test
    fun btlyService() {
        val convert = btlyService.shorten("https://google.com")
        println()
    }
}
