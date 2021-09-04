package jpabook.jpashop.repository.dto

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class SimpleOrderDto(
    val orderId: Long,
    val userName: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address
)