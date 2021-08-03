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
//    @Rollback(false)
    fun testMember() {
        // given
        val member = Member(username = "memberA")

        // when
        val savedId = memberRepository.save(member)
        val findMember = memberRepository.find(savedId)

        // then
        assert(findMember.id == member.id)
        assert(findMember.username == member.username)

        /**
         * 같은 트랜젝션 영속성 컨텍스트 내에서 id 값이 같으면 1차 캐싱되어있던 엔티티를 사용함
         * (find 할 때 select 쿼리조차 안날리고 캐싱되어있던 엔티티를 그대로 가져옴)
         * 따라서 아래 두 객체는 같은 객체
         */
        assert(member == findMember)
    }
}