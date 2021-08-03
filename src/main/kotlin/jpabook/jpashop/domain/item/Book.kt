package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book(
    author: String,
    isbn: String,
    name: String,
    price: Int,
    stockQuantity: Int,
    categories: List<Category> = arrayListOf()
) : Item(name, price, stockQuantity, categories) {

    /**
     * 저자
     */
    val author: String = author

    /**
     * 도서 번호
     */
    val isbn: String = isbn
}