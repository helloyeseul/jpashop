package jpabook.jpashop.api

import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApiController(
    private val memberService: MemberService
) {

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Validated member: Member) =
        CreateMemberResponse(memberService.join(member))

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Validated request: CreateMemberRequest) =
        CreateMemberResponse(memberService.join(Member(request.name)))

    data class CreateMemberRequest(
        @field:Length(min = 2, max = 10, message = "{CreateMemberRequest.Name.Length.message}")
        val name: String
    )

    data class CreateMemberResponse(val id: Long)
}
