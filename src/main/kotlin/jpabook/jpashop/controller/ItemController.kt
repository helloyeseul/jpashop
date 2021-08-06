package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.extensions.requireNotBlank
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(
    private val itemService: ItemService
) {

    @GetMapping("/items/new")
    fun createForm(model: Model): String = "items/createItemForm".also {
        model.addAttribute("form", BookForm())
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String = "redirect:/".also {
        itemService.saveItem(
            Book(
                name = requireNotBlank(form.name),
                author = requireNotBlank(form.author),
                isbn = requireNotBlank(form.isbn),
                price = requireNotNull(form.price?.takeIf { it >= 0 }),
                stockQuantity = requireNotNull(form.stockQuantity)
            )
        )
    }

    @GetMapping("/items")
    fun list(model: Model): String = "items/itemList".also {
        model.addAttribute("items", itemService.findItems())
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String = "items/updateItemForm".also {
        val book: Book? = itemService.findOne(itemId) as? Book
        checkNotNull(book)
        val form = BookForm(
            id = book.id,
            name = book.name,
            price = book.price,
            stockQuantity = book.stockQuantity,
            author = book.author,
            isbn = book.isbn
        )
        model.addAttribute("form", form)
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(
        @PathVariable itemId: Long,
        @ModelAttribute("form") form: BookForm
    ): String = "redirect:/items".also {
        itemService.saveItem(
            Book(
                id = requireNotNull(form.id),
                name = requireNotBlank(form.name),
                price = requireNotNull(form.price?.takeIf { it > 0 }),
                stockQuantity = requireNotNull(form.stockQuantity?.takeIf { it > 0 }),
                author = requireNotBlank(form.author),
                isbn = requireNotBlank(form.isbn)
            )
        )
    }
}