package jpabook.jpashop.controller

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.extensions.requireNotBlank
import jpabook.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members/new")
    fun createForm(model: Model) = "members/createMemberForm".also {
        model.addAttribute("memberForm", MemberForm())
    }

    @PostMapping("/members/new")
    fun create(form: MemberForm, result: BindingResult): String {

        if (result.hasErrors()) {
            return "members/createMemberForm"
        }

        val address = Address(
            requireNotBlank(form.city),
            requireNotBlank(form.street),
            requireNotBlank(form.zipcode)
        )

        val member = Member(
            requireNotBlank(form.name),
            address
        )

        memberService.join(member)

        return "redirect:/"
    }
}