package jpabook.jpashop.api.member.request

import org.hibernate.validator.constraints.Length

data class CreateMemberRequest(
    @field:Length(min = 2, max = 10, message = "{CreateMemberRequest.Name.Length.message}")
    val name: String
)
