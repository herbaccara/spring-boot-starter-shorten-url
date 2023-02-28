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
class ShortUrlServiceTest {

    @Autowired
    lateinit var shortUrlServices: List<ShortUrlService>

    @Autowired
    lateinit var naverShortUrlService: NaverShortUrlService

    @Autowired
    lateinit var btlyService: BitlyService

    @Test
    fun naverShortUrlService() {
        val convert = naverShortUrlService.shorten("https://google.com")
        println()
    }

    @Test
    fun btlyService() {
        val convert = btlyService.shorten("https://google.com")
        println()
    }
}
