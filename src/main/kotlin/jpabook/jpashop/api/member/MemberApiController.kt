package jpabook.jpashop.api.member

import jpabook.jpashop.api.member.request.CreateMemberRequest
import jpabook.jpashop.api.member.request.UpdateMemberRequest
import jpabook.jpashop.api.member.response.CreateMemberResponse
import jpabook.jpashop.api.member.response.FindMemberResponse
import jpabook.jpashop.api.member.response.UpdateMemberResponse
import jpabook.jpashop.api.response.BaseResponse
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class MemberApiController(
    private val memberService: MemberService
) {

    /**
     * 회원 등록
     */
    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Validated member: Member) =
        CreateMemberResponse(memberService.join(member))

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Validated request: CreateMemberRequest) =
        CreateMemberResponse(memberService.join(Member(request.name)))

    /**
     * 회원 조회
     */
    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> = memberService.findMembers()

    @GetMapping("/api/v2/members")
    fun membersV2(): BaseResponse<List<FindMemberResponse>> =
        memberService.findMembers()
            .map { FindMemberResponse(it.name) }
            .let { BaseResponse(it) }

    /**
     * 회원 수정
     */
    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody @Validated request: UpdateMemberRequest
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val member = memberService.findOne(id)
        return UpdateMemberResponse(id, member!!.name)
    }
}
