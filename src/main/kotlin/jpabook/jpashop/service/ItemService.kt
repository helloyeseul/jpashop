package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Item
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

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item = itemRepository.findOne(itemId)
}