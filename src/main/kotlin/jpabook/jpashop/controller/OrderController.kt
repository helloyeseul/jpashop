package jpabook.jpashop.controller

import jpabook.jpashop.service.ItemService
import jpabook.jpashop.service.MemberService
import jpabook.jpashop.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class OrderController(
    private val orderService: OrderService,
    private val memberService: MemberService,
    private val itemService: ItemService
) {

    @GetMapping("/order")
    fun createForm(model: Model): String = "orders/orderForm".also {
        model.addAttribute("members", memberService.findMembers())
        model.addAttribute("items", itemService.findItems())
    }

    @PostMapping("/order")
    fun order(
        @RequestParam("memberId") memberId: Long,
        @RequestParam("itemId") itemId: Long,
        @RequestParam("count") count: Int
    ): String = "redirect:/orders".also {
        orderService.order(memberId, itemId, count)
    }
}