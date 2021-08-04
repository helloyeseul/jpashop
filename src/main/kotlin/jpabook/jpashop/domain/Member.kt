package jpabook.jpashop.domain

import jpabook.jpashop.domain.base.BaseEntity
import javax.persistence.*

@Entity
class Member(
    name: String,
    address: Address? = null
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    override val id: Long? = null

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
    val orders: MutableList<Order> = arrayListOf()
}
