package jpabook.jpashop.service

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.base.persistAndGetId
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
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

    @Test
    fun find() {
        // given
        val member1 = createMember("이예슬")
        val member2 = createMember("이현택")

        val memberId1 = em.persistAndGetId(member1)
        val memberId2 = em.persistAndGetId(member2)

        val book1 = createBook("JPA", "JPA", 5000, 20)
        val book2 = createBook("KOTLIN", "kotlin", 10000, 10)

        val bookId1 = em.persistAndGetId(book1)
        val bookId2 = em.persistAndGetId(book2)

        // when
        val order1 = orderService.order(memberId1, bookId1, 2)
        val order2 = orderService.order(memberId1, bookId2, 1)
        val order3 = orderService.order(memberId2, bookId1, 2)
        val order4 = orderService.order(memberId2, bookId2, 3)

        // then
        assertEquals(4, orderService.findOrders(OrderSearch("이")).size)
        assertEquals(4, orderService.findOrders(OrderSearch(orderStatus = OrderStatus.ORDER)).size)

        assertEquals(2, orderService.findOrders(OrderSearch("이예슬")).size)
        assertEquals(0, orderService.findOrders(OrderSearch("이", OrderStatus.CANCEL)).size)

        orderService.cancelOrder(order1)
        assertEquals(1, orderService.findOrders(OrderSearch(orderStatus = OrderStatus.CANCEL)).size)
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
