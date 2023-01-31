package dev.marcel.sessionredis.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono

@RestController
class SessionHandler {

    @GetMapping("/websession")
    fun getSession(session: WebSession): Mono<String?>? {
        session.attributes.putIfAbsent("note", "Howdy Cosmic Spheroid!")
        return Mono.justOrEmpty(session.attributes["note"] as String?)
    }
}
