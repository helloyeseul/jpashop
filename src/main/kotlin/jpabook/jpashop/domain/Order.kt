package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "orders")
class Order(
    member: Member,
    orderItems: MutableList<OrderItem> = arrayListOf(),
    delivery: Delivery,
    orderDate: LocalDateTime,
    status: OrderStatus
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    val id: Long = 0

    /**
     * 주문자
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member = member
        protected set

    /**
     * 주문 목록
     */
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = orderItems

    /**
     * 배송지 정보
     */
    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery = delivery
        protected set

    /**
     * 주문 시간
     *
     * Tips) Date 타입을 써서 어노테이션으로 로컬시간 매핑하는 방법 대신,
     * LocalDateTime 을 쓰면 하이버네이트가 알아서 매핑해서 저장해줌
     */
    val orderDate: LocalDateTime = orderDate

    /**
     * 주문 상태 [ORDER, CANCEL]
     */
    @Enumerated(EnumType.STRING)
    val status: OrderStatus = status


    /**
     * 연관관계 메서드
     */
    fun updateMember(member: Member) {
        this.member = member
        member.orders += this
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems += orderItem
        orderItem.updateOrder(this)
    }

    fun updateDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.updateOrder(this)
    }
}