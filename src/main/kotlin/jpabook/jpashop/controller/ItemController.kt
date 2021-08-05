package jpabook.jpashop.controller

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.extensions.requireNotBlank
import jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
}