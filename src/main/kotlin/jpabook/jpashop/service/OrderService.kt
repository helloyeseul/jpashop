package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository
) {

    // 주문
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {

        val member = memberRepository.findOne(memberId)
        checkNotNull(member?.address)

        val item = itemRepository.findOne(itemId)
        checkNotNull(item)

        // 배송지
        val delivery = Delivery(
            address = member?.address!!
        )

        // 주문 상품 생성
        val orderItem = OrderItem(
            item = item,
            orderPrice = item.price,
            count = count
        )

        // 주문 생성
        val order = Order(
            member = member,
            orderItems = arrayListOf(orderItem),
            delivery = delivery
        )

        // 주문 저장
        orderRepository.save(order)

        return requireNotNull(order.id)
    }

    // 취소


    // 검색

}