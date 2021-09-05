package jpabook.jpashop.api.order

import jpabook.jpashop.api.response.BaseResponse
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.simplequery.OrderDto
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
    val orderRepository: OrderRepository
) {

    @GetMapping("/api/v2/orders")
    fun ordersV2(): BaseResponse<List<OrderDto>> =
        orderRepository.findAllByString(OrderSearch())
            .map { OrderDto.fromOrder(it) }
            .let { BaseResponse(it) }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): BaseResponse<List<OrderDto>> =
        orderRepository.findAllWithItem()
            .map { OrderDto.fromOrder(it) }
            .let { BaseResponse(it) }

    @GetMapping("/api/v4/orders")
    fun ordersV3(
        @Param("offset") offset: Int = 0,
        @Param("limit") limit: Int = 100,
    ): BaseResponse<List<OrderDto>> =
        orderRepository.findAll(offset, limit)
            .map { OrderDto.fromOrder(it) }
            .let { BaseResponse(it) }
}