package jpabook.jpashop.api.order

import jpabook.jpashop.api.response.BaseResponse
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.query.OrderDto
import jpabook.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
    val orderRepository: OrderRepository,
    val orderQueryRepository: OrderQueryRepository
) {

    @GetMapping("/api/v2/orders")
    fun ordersV2(): BaseResponse<List<OrderDto>> =
        orderRepository.findAllByString(OrderSearch())
            .map { OrderDto.fromOrder(it) }
            .let { BaseResponse(it) }

//    @GetMapping("/api/v3/orders")
//    fun ordersV3(): BaseResponse<List<OrderDto>> =
//        orderRepository.findAllWithItem()
//            .map { OrderDto.fromOrder(it) }
//            .let { BaseResponse(it) }

    @GetMapping("/api/v3/orders")
    fun ordersV3(
        @RequestParam("offset", defaultValue = "0") offset: Int = 0,
        @RequestParam("limit", defaultValue = "100") limit: Int = 100,
    ): BaseResponse<List<OrderDto>> =
        orderRepository.findAll(offset, limit)
            .map { OrderDto.fromOrder(it) }
            .let { BaseResponse(it) }

    @GetMapping("/api/v4/orders")
    fun ordersV4(): BaseResponse<List<OrderDto>> =
        BaseResponse(orderQueryRepository.findAll())

    @GetMapping("/api/v5/orders")
    fun ordersV5(): BaseResponse<List<OrderDto>> =
        BaseResponse(orderQueryRepository.findAllOptimization())
}