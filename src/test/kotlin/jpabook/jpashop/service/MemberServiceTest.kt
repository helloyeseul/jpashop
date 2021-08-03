package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberServiceTest {

    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    @Rollback(false)
    fun join() {
        // given
        val member = Member(name = "이예슬")

        // when
        val savedId = memberService.join(member)

        // then
        assert(member == memberRepository.findOne(savedId))
    }

    @Test
    fun deplication() {
        // given
        val member1 = Member(name = "이현택")
        val member2 = Member(name = "이현택")

        // when
        memberService.join(member1)

        // then
        assertThrows<IllegalStateException> {
            memberService.join(member2)
        }
    }
}