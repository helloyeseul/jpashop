package jpabook.jpashop.repository.order.query

import com.fasterxml.jackson.annotation.JsonIgnore
import jpabook.jpashop.domain.OrderItem

data class OrderItemDto(
    @JsonIgnore
    val orderId: Long,
    val itemName: String,
    val orderPrice: Int,
    val count: Int
) {

    companion object {

        fun fromOrderItem(orderItem: OrderItem) = OrderItemDto(
            orderId = orderItem.order!!.id!!,
            itemName = orderItem.item.name,
            orderPrice = orderItem.orderPrice,
            count = orderItem.count,
        )
    }
}