package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
data class Movie(

    /**
     * 감독
     */
    val director: String,

    /**
     * 배우
     */
    val actor: String,

    override val id: Long,

    override val name: String,

    override val price: Int,

    override val stockQuantity: Int

) : Item(id, name, price, stockQuantity)