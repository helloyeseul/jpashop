package jpabook.jpashop.repository.order.simplequery

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderSimpleQueryRepository(
    private val em: EntityManager
) {

    fun findAllDto(): List<OrderSimpleDto> =
        em.createQuery(
            """select new jpabook.jpashop.repository.order.simplequery.OrderSimpleDto(o.id, m.name, o.orderDate, o.status, m.address) 
                |from Order o
                |join o.member m
                |join o.delivery d
            """.trimMargin(),
            OrderSimpleDto::class.java
        ).resultList
}