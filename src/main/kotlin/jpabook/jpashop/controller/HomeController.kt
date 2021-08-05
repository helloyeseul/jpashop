package jpabook.jpashop.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    private val log = LoggerFactory.getLogger(javaClass)

    @RequestMapping("/")
    fun home() = "home".also {
        log.info("home controller")
    }
}