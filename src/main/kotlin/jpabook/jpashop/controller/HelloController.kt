package jpabook.jpashop.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelloController {

    @GetMapping("hello")
    fun hello(model: Model) = "hello".also {
        model.addAttribute("data", "hello!!!")
    }
}