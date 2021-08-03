package jpabook.jpashop.domain

import javax.persistence.*

@Entity
data class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long,

    /**
     * 이름
     */
    val name: String,

    /**
     * 주소
     */
    @Embedded
    val address: Address,

    /**
     * 주문 목록
     */
    @OneToMany(mappedBy = "member")
    val orders: List<Order> = arrayListOf()
)
