package jpabook.jpashop.controller

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookForm(

    val id: Long? = null,

    @field:NotBlank
    val name: String? = null,

    @field:NotNull
    @field:Min(0)
    val price: Int? = null,

    @field:NotNull
    @field:Min(0)
    val stockQuantity: Int? = null,

    @field:NotBlank
    val author: String? = null,

    @field:NotBlank
    val isbn: String? = null
)
