package jpabook.jpashop.domain

import jpabook.jpashop.domain.base.BaseEntity
import javax.persistence.*
import javax.persistence.FetchType.LAZY


@Entity
class Delivery(
    address: Address,
    status: DeliveryStatus = DeliveryStatus.READY
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    override val id: Long? = null

    /**
     * 주문 정보
     */
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Order? = null

    /**
     * 배송 주소
     */
    val address: Address = address

    /**
     * 배송 상태 [READY, COMP]
     */
    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus = status
}
