package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long,

    /**
     * 카테고리명
     */
    val name: String,

    /**
     * 해당 카테고리에 속한 상품 목록
     */
    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: List<Item> = arrayListOf(),

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Category?,

    @OneToMany(mappedBy = "parent")
    val children: List<Category> = arrayListOf()
)