package jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
data class Address(

    /**
     * 도시명
     */
    val city: String,

    /**
     * 거리명
     */
    val street: String,

    /**
     * 우편번호
     */
    val zipcode: String
)
