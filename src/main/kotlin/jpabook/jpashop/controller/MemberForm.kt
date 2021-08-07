package jpabook.jpashop.controller

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank


data class MemberForm(

    @field:Length(min = 2, max = 10, message = "{Member.Name.Length.message}")
    val name: String? = null,

    @field:NotBlank
    val city: String? = null,

    @field:NotBlank
    val street: String? = null,

    @field:NotBlank
    val zipcode: String? = null
)
