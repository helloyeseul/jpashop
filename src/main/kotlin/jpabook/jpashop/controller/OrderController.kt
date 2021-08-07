package jpabook.jpashop.controller

import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.service.ItemService
import jpabook.jpashop.service.MemberService
import jpabook.jpashop.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Positive

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
        @RequestParam("count") @Validated @Positive count: Int
    ): String = "redirect:/orders".also {
        orderService.order(memberId, itemId, count)
    }

    @GetMapping("/orders")
    fun orderList(
        @ModelAttribute("orderSearch") orderSearch: OrderSearch,
        model: Model
    ): String = "orders/orderList".also {
        model.addAttribute("orders", orderService.findOrders(orderSearch))
    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): String =
        "redirect:/orders".also {
            orderService.cancelOrder(orderId)
        }
}