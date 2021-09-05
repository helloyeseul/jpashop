package jpabook.jpashop.repository.order.query

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderQueryRepository(
    val em: EntityManager
) {

    private fun selectOrders(): List<OrderDto> =
        em.createQuery(
            """select new jpabook.jpashop.repository.order.query.OrderDto(o.id, m.name, o.orderDate, o.status, m.address) 
             |from Order o
             |join o.member m
             |join o.delivery d
         """.trimMargin(),
            OrderDto::class.java
        ).resultList

    private fun selectOrderItems(orderId: Long): List<OrderItemDto> =
        em.createQuery(
            """select new jpabook.jpashop.repository.order.query.OrderItemDto(oi.item.name, oi.orderPrice, oi.count) 
             |from OrderItem oi
             |join oi.item i 
             |where oi.order.id = :orderId
         """.trimMargin(),
            OrderItemDto::class.java
        )
            .setParameter("orderId", orderId)
            .resultList

    fun findAll(): List<OrderDto> =
        selectOrders().onEach {
            it.orderItems += selectOrderItems(it.orderId)
        }
}