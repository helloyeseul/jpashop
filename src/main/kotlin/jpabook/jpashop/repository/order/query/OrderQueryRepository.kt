package jpabook.jpashop.repository.order.query

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderQueryRepository(
    val em: EntityManager
) {

    private fun findOrders(): List<OrderDto> =
        em.createQuery(
            """select new jpabook.jpashop.repository.order.query.OrderDto(o.id, m.name, o.orderDate, o.status, m.address) 
             |from Order o
             |join o.member m
             |join o.delivery d
         """.trimMargin(),
            OrderDto::class.java
        ).resultList

    private fun findOrderItems(orderId: Long): List<OrderItemDto> =
        em.createQuery(
            """select new jpabook.jpashop.repository.order.query.OrderItemDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count) 
             |from OrderItem oi
             |join oi.item i 
             |where oi.order.id = :orderId
         """.trimMargin(),
            OrderItemDto::class.java
        )
            .setParameter("orderId", orderId)
            .resultList

    fun findAll(): List<OrderDto> =
        findOrders().onEach { it.orderItems += findOrderItems(it.orderId) }

    fun findAllOptimization(): List<OrderDto> =
        findOrders().also { orders ->
            val orderItemsMap = findOrderItemMap(orders.map { it.orderId })
            orders.forEach { it.orderItems += orderItemsMap[it.orderId]!! }
        }

    private fun findOrderItemMap(orderIds: List<Long>): Map<Long, List<OrderItemDto>> {
        val orderItems = em.createQuery(
            """select new jpabook.jpashop.repository.order.query.OrderItemDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count) 
                 |from OrderItem oi
                 |join oi.item i 
                 |where oi.order.id in :orderIds
                """.trimMargin(),
            OrderItemDto::class.java
        )
            .setParameter("orderIds", orderIds)
            .resultList

        return orderItems.groupBy { it.orderId }
    }
}