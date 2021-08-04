package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.Item
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class OrderItem(item: Item, orderPrice: Int, count: Int) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long? = null

    /**
     * 주문 상품
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    val item: Item = item.also { it.removeStock(count) }

    /**
     * 주문 정보
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    /**
     * 주문 당시 아이템 가격
     * (가격 변경 또는 할인 적용 등 item.price 와 다를 수 있음)
     */
    val orderPrice: Int = orderPrice

    /**
     * 주문 수량
     */
    val count: Int = count

    /**
     * 총 주문 가격
     */
    val totalPrice: Int
        get() = orderPrice * count

    fun cancel() {
        item.addStock(count)
    }
}
