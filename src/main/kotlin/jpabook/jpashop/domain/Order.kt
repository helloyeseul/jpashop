package jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Long,

    /**
     * 주문자
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    /**
     * 주문 목록
     */
    @OneToMany(mappedBy = "order")
    val orderItems: List<OrderItem> = arrayListOf(),

    /**
     * 배송지 정보
     */
    @OneToOne
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery,

    /**
     * 주문 시간
     *
     * Tips) Date 타입을 써서 어노테이션으로 로컬시간 매핑하는 방법 대신,
     * LocalDateTime 을 쓰면 하이버네이트가 알아서 매핑해서 저장해줌
     */
    val orderDate: LocalDateTime,

    /**
     * 주문 상태 [ORDER, CANCEL]
     */
    @Enumerated(EnumType.STRING)
    val status: OrderStatus
)
