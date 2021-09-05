package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
    val orderItems: List<OrderItemDto>
) {
    companion object {

        fun fromOrder(order: Order) = OrderDto(
            orderId = order.id!!,
            name = order.member.name,
            orderDate = order.orderDate,
            orderStatus = order.status,
            address = order.delivery.address,
            orderItems = order.orderItemList.map { OrderItemDto.fromOrderItem(it) },
        )
    }
}