package jpabook.jpashop.domain

import javax.persistence.*


@Entity
data class Delivery(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    val id: Long,

    /**
     * 주문 정보
     */
    @OneToOne(mappedBy = "delivery")
    val order: Order,

    /**
     * 배송 주소
     */
    val address: Address,

    /**
     * 배송 상태 [READY, COMP]
     */
    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus
)
