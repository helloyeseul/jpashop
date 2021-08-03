package jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Member(name: String, address: Address? = null, orders: MutableList<Order> = arrayListOf()) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0

    /**
     * 이름
     */
    val name: String = name

    /**
     * 주소
     */
    @Embedded
    val address: Address? = address

    /**
     * 주문 목록
     */
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = orders
}
