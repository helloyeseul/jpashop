package jpabook.jpashop.api.order.response

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleResponse(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address
) {
    companion object {

        fun fromOrder(order: Order) = OrderSimpleResponse(
            orderId = order.id!!,
            name = order.member.name, /* LAZY 로딩 초기화 */
            orderDate = order.orderDate,
            orderStatus = order.status,
            address = order.delivery.address /* LAZY 로딩 초기화 */
        )
    }
}