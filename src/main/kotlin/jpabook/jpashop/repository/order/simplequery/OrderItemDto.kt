package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.OrderItem

data class OrderItemDto(
    val itemName: String,
    val orderPrice: Int,
    val count: Int
) {

    companion object {

        fun fromOrderItem(orderItem: OrderItem) = OrderItemDto(
            itemName = orderItem.item.name,
            orderPrice = orderItem.orderPrice,
            count = orderItem.count,
        )
    }
}