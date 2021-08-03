package jpabook.jpashop.domain

import javax.persistence.*
import javax.persistence.FetchType.LAZY


@Entity
class Delivery(order: Order, address: Address, status: DeliveryStatus) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    val id: Long = 0

    /**
     * 주문 정보
     */
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Order = order
        protected set

    /**
     * 배송 주소
     */
    val address: Address = address

    /**
     * 배송 상태 [READY, COMP]
     */
    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus = status

    fun updateOrder(order: Order) {
        this.order = order
    }
}
