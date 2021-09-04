package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleDto(
    val orderId: Long,
    val userName: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address
)