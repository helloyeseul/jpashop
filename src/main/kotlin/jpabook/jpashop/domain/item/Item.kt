package jpabook.jpashop.domain.item

import jpabook.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
sealed class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    open val id: Long,

    /**
     * 상품명
     */
    open val name: String,

    /**
     * 상품 가격
     */
    open val price: Int,

    /**
     * 재고 수량
     */
    open val stockQuantity: Int,

    /**
     * 카테고리 목록
     */
    @ManyToMany(mappedBy = "items")
    open val categories: List<Category> = arrayListOf()
)