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
    fun updateItem(itemDto: UpdateItemDto) {
        when (val item = itemRepository.findOne(itemDto.itemId)) {
            is Book -> item.update(
                itemDto.name,
                itemDto.price,
                itemDto.stockQuantity,
                itemDto.author,
                itemDto.isbn,
            )

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