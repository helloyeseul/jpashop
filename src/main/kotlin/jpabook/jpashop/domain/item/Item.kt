package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(name: String, price: Int, stockQuantity: Int, categories: List<Category>) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0

    /**
     * 상품명
     */
    val name: String = name

    /**
     * 상품 가격
     */
    val price: Int = price

    /**
     * 재고 수량
     */
    val stockQuantity: Int = stockQuantity

    /**
     * 카테고리 목록
     */
    @ManyToMany(mappedBy = "items")
    val categories: List<Category> = categories
}