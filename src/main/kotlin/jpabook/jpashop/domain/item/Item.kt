package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import jpabook.jpashop.domain.base.BaseEntity
import jpabook.jpashop.exception.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    name: String,
    price: Int,
    stockQuantity: Int,
    categories: List<Category>
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    override val id: Long? = null

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
    var stockQuantity: Int = stockQuantity
        protected set

    /**
     * 카테고리 목록
     */
    @ManyToMany(mappedBy = "items")
    val categories: List<Category> = categories

    /**
     * 재고 증가
     */
    fun addStock(quantity: Int) {
        this.stockQuantity += quantity
    }

    /**
     * 재고 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = this.stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        this.stockQuantity = restStock
    }
}