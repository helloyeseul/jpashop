package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
class Order(
    member: Member,
    delivery: Delivery,
    orderItems: MutableList<OrderItem>
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    val id: Long? = null

    /**
     * 주문자
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    val member: Member = member.also { it.orders += this }

    /**
     * 배송지 정보
     */
    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery = delivery.also { it.order = this }
        set(value) {
            field = value
            value.order = this
        }

    /**
     * 주문 목록
     */
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    private val orderItems: MutableList<OrderItem> = arrayListOf<OrderItem>().also {
        orderItems.forEach { item -> addOrderItem(item) }
    }

    val orderItemList: List<OrderItem>
        get() = orderItems.toList()

    /**
     * 주문 시간
     *
     * Tips) Date 타입을 써서 어노테이션으로 로컬시간 매핑하는 방법 대신,
     * LocalDateTime 을 쓰면 하이버네이트가 알아서 매핑해서 저장해줌
     */
    val orderDate: LocalDateTime = LocalDateTime.now()

    /**
     * 주문 상태 [ORDER, CANCEL]
     */
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER

    /**
     * 총 주문 가격
     */
    val totalPrice: Int
        get() = orderItems.sumOf { it.totalPrice }

    /* 연관 관계 메서드 */

    fun addOrderItem(orderItem: OrderItem) {
        orderItems += orderItem
        orderItem.order = this
    }

    /* 비지니스 로직 */

    fun cancel() {
        check(delivery.status == DeliveryStatus.READY) {
            "${member.name}님이 주문하신 ${orderItems[0].item.name} 상품은 취소가 불가능합니다. (배송 완료)"
        }
        status = OrderStatus.CANCEL
        orderItems.forEach { it.cancel() }
    }
}