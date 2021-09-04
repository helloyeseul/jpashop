package jpabook.jpashop.api.order

import jpabook.jpashop.api.order.response.SimpleOrderResponse
import jpabook.jpashop.api.response.BaseResponse
import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderSimpleApiController(
    val orderRepository: OrderRepository
) {

    /**
     * 주문 조회
     */
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> = orderRepository.findAllByString(OrderSearch())

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): BaseResponse<List<SimpleOrderResponse>> =
        orderRepository.findAllByString(OrderSearch())
            .map { SimpleOrderResponse.fromOrder(it) }
            .let { BaseResponse(it) }
}