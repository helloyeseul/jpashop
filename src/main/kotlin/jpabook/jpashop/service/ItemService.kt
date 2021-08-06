package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Album
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.domain.item.Movie
import jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository
) {

    @Transactional
    fun saveItem(item: Item): Long {
        itemRepository.save(item)
        return item.id!!
    }

    @Transactional
    fun updateItem(
        itemId: Long,
        name: String? = null,
        price: Int? = null,
        stockQuantity: Int? = null,
        author: String? = null,
        isbn: String? = null
    ) {
        val item = itemRepository.findOne(itemId)?.apply {
            name?.let { updateName(it) }
            price?.takeIf { it >= 0 }?.let { updatePrice(it) }
            stockQuantity?.takeIf { it >= 0 }?.let { updateStock(it) }
        }

        when (item) {
            is Book -> item.run {
                author?.let { updateAuthor(it) }
                isbn?.let { updateIsbn(it) }
            }
            is Album -> {
                // update album
            }
            is Movie -> {
                // update movie
            }
        }
    }

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item? = itemRepository.findOne(itemId)
}