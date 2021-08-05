package jpabook.jpashop.controller

data class MemberForm(
    val name: String? = null,
    val city: String? = null,
    val street: String? = null,
    val zipcode: String? = null
)
