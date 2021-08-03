package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
data class Book(

    /**
     * 저자
     */
    val author: String,

    /**
     * 도서 번호
     */
    val isbn: String,

    override val id: Long,

    override val name: String,

    override val price: Int,

    override val stockQuantity: Int

) : Item(id, name, price, stockQuantity)