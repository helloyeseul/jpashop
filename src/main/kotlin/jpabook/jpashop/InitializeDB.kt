package jpabook.jpashop

import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitializeDB(
    val initService: InitService
) {

    @PostConstruct
    fun init() {
        initService.init1()
        initService.init2()
    }

    @Component
    @Transactional
    class InitService(
        val em: EntityManager
    ) {

        fun init1() {
            val member = Member("userA", Address("서울", "123", "123"))
            em.persist(member)

            val book1 = Book(
                name = "JPA1",
                price = 10000,
                stockQuantity = 100,
                author = "JPA",
                isbn = "123"
            )
            em.persist(book1)

            val book2 = Book(
                name = "JPA2",
                price = 20000,
                stockQuantity = 80,
                author = "JPA",
                isbn = "123"
            )
            em.persist(book2)

            val orderItem1 = OrderItem(book1, book1.price, 1)
            val orderItem2 = OrderItem(book2, book1.price, 2)

            val order = Order(member, Delivery(member.address!!), listOf(orderItem1, orderItem2))
            em.persist(order)
        }

        fun init2() {
            val member = Member("userB", Address("경기", "222", "1111"))
            em.persist(member)

            val book1 = Book(
                name = "Spring1",
                price = 20000,
                stockQuantity = 50,
                author = "Spring",
                isbn = "123"
            )
            em.persist(book1)


            val book2 = Book(
                name = "Spring2",
                price = 40000,
                stockQuantity = 200,
                author = "Spring",
                isbn = "123"
            )
            em.persist(book2)

            val orderItem1 = OrderItem(book1, book1.price, 4)
            val orderItem2 = OrderItem(book2, book1.price, 3)

            val order = Order(member, Delivery(member.address!!), listOf(orderItem1, orderItem2))
            em.persist(order)
        }
    }
}