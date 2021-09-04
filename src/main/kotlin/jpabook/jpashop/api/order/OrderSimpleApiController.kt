package jpabook.jpashop.api.order

import jpabook.jpashop.api.order.response.OrderSimpleResponse
import jpabook.jpashop.api.response.BaseResponse
import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.simplequery.OrderSimpleDto
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderSimpleApiController(
    val orderRepository: OrderRepository,
    val orderSimpleQueryRepository: OrderSimpleQueryRepository,
) {

    /**
     * 주문 조회
     */
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> = orderRepository.findAllByString(OrderSearch())

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): BaseResponse<List<OrderSimpleResponse>> =
        orderRepository.findAllByString(OrderSearch())
            .map { OrderSimpleResponse.fromOrder(it) }
            .let { BaseResponse(it) }

    /* 패치 조인 */
    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): BaseResponse<List<OrderSimpleResponse>> =
        orderRepository.findAll(OrderSearch())
            .map { OrderSimpleResponse.fromOrder(it) }
            .let { BaseResponse(it) }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): BaseResponse<List<OrderSimpleDto>> =
        BaseResponse(orderSimpleQueryRepository.findAllDto())
}