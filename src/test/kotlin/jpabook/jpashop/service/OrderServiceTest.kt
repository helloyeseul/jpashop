package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
internal class OrderServiceTest {

    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var em: EntityManager

    @Test
    fun order() {
        // given
        val price = 10000
        val stock = 200
        val orderCount = 2

        val member = createMember("회원1")
        val book = createBook("JPA", "이예슬", price, stock)

        // when
        val orderId = orderService.order(
            em.persist(member).run { requireNotNull(member.id) },
            em.persist(book).run { requireNotNull(book.id) },
            orderCount
        )

        // then
        val order = orderRepository.findOne(orderId)
        assertEquals(OrderStatus.ORDER, order.status, "상품 주문 상태 - ORDER")
        assertEquals(1, order.orderItemList.size, "주문한 상품 수량")
        assertEquals(price * orderCount, order.totalPrice, "주문 가격 = 가격 * 수량")
        assertEquals(stock - orderCount, book.stockQuantity, "주문하면 재고 수량 감소")
        assertEquals(1, member.orders.size, "주문하면 회원 주문 목록에 반영")
    }

    @Test
    fun notEnoughStock() {
        // given
        val price = 20000
        val stock = 2
        val orderCount = 5

        val member = createMember("이예슬")
        val book = createBook("KOTLIN", "코틀린", price, stock)

        // when
        assertThrows<NotEnoughStockException> {
            orderService.order(
                em.persist(member).run { requireNotNull(member.id) },
                em.persist(book).run { requireNotNull(book.id) },
                orderCount
            )
        }

        // then
    }

    @Test
    fun cancel() {
        // given
        val stock = 10
        val orderCount = 2

        val member = createMember("이예슬")
        val book = createBook("JPA", "저자", 10000, stock)

        val orderId = orderService.order(
            em.persist(member).run { requireNotNull(member.id) },
            em.persist(book).run { requireNotNull(book.id) },
            orderCount
        )

        // when
        orderService.cancelOrder(orderId)

        // then
        val order = orderRepository.findOne(orderId)

        assertEquals(OrderStatus.CANCEL, order.status, "주문 취소 상태 변경")
        assertEquals(stock, book.stockQuantity, "주문 취소 후 재고 수량")
    }

    private fun createBook(name: String, author: String, price: Int, stock: Int) = Book(
        name = name,
        author = author,
        isbn = "12345",
        price = price,
        stockQuantity = stock
    )

    private fun createMember(name: String) = Member(
        name = name,
        address = Address("서울", "경기", "12345")
    )
}
