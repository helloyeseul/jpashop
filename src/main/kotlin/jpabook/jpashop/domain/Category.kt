package jpabook.jpashop.domain

import jpabook.jpashop.domain.base.BaseEntity
import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class Category(
    name: String,
    items: List<Item>,
    parent: Category? = null,
    children: MutableList<Category> = arrayListOf()
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    override val id: Long? = null

    /**
     * 카테고리명
     */
    val name: String = name

    /**
     * 해당 카테고리에 속한 상품 목록
     */
    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: List<Item> = items

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = parent
        protected set

    @OneToMany(mappedBy = "parent")
    val children: MutableList<Category> = children


    fun addChildCategory(child: Category) {
        this.children += child
        child.parent = this
    }
}