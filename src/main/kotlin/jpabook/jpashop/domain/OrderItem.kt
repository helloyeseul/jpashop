package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class OrderItem(item: Item, order: Order, orderPrice: Int, count: Int) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null

    /**
     * 주문 상품
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    val item: Item = item

    /**
     * 주문 정보
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order = order
        protected set

    /**
     * 주문 가격
     */
    val orderPrice: Int = orderPrice

    /**
     * 주문 수량
     */
    val count: Int = count

    fun updateOrder(order: Order) {
        this.order = order
    }
}
