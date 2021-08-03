package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("M")
class Movie(
    director: String,
    actor: String,
    name: String,
    price: Int,
    stockQuantity: Int,
    categories: List<Category> = arrayListOf()
) : Item(name, price, stockQuantity, categories) {

    /**
     * 감독
     */
    val director: String = director

    /**
     * 배우
     */
    val actor: String = actor
}
