package jpabook.jpashop.service

data class UpdateItemDto(
    val itemId: Long,
    val name: String,
    val price: Int,
    val stockQuantity: Int,
    val author: String,
    val isbn: String
)