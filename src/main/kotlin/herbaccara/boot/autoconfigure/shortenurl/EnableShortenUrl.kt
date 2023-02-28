package herbaccara.boot.autoconfigure.shortenurl

import org.springframework.context.annotation.Import
import java.lang.annotation.*

@Target
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(ShortenUrlAutoConfiguration::class)
annotation class EnableShortenUrl
