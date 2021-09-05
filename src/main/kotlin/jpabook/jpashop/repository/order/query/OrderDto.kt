package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address
) {

    val orderItems: MutableList<OrderItemDto> = mutableListOf()
    
    companion object {

        fun fromOrder(order: Order) = OrderDto(
            orderId = order.id!!,
            name = order.member.name,
            orderDate = order.orderDate,
            orderStatus = order.status,
            address = order.delivery.address
        ).apply {
            orderItems += order.orderItemList.map { OrderItemDto.fromOrderItem(it) }
        }
    }
}