package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    artist: String,
    etc: String,
    name: String,
    price: Int,
    stockQuantity: Int,
    categories: List<Category> = arrayListOf(),
    id: Long? = null
) : Item(name, price, stockQuantity, categories, id) {

    /**
     * 아티스트
     */
    val artist: String = artist

    /**
     * 기타 정보
     */
    val etc: String = etc
}