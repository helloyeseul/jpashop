package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.repository.ItemRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class ItemServiceTest {

    @Autowired
    private lateinit var itemService: ItemService

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Test
    fun saveItem() {
        // given
        val item = Book("드미트리", "123423", "코틀린 인 액션", 2000, 200)

        // when
        val savedId = itemService.saveItem(item)

        // then
        assert(item == itemRepository.findOne(savedId))
    }
}