package herbaccara.shortenurl

interface ShortUrlService {

    fun shorten(url: String): String
}
