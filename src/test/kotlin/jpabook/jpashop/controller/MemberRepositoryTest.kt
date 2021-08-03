package jpabook.jpashop.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun testMember() {
        // given
        val member = Member(username = "memberA")

        // when
        val savedId = memberRepository.save(member)
        val findMember = memberRepository.find(savedId)

        // then
        assert(findMember.id == member.id)
        assert(findMember.username == member.username)
    }
}