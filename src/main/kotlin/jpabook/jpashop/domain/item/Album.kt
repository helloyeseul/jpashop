package jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
data class Album(

    /**
     * 아티스트
     */
    val artist: String,

    /**
     * 기타 정보
     */
    val etc: String,

    override val id: Long,

    override val name: String,

    override val price: Int,

    override val stockQuantity: Int

) : Item(id, name, price, stockQuantity)