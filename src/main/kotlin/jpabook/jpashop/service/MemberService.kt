package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    // 회원 가입
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id!!
    }

    /**
     * 실무에서는 name 필드에 unique 제약 조건 걸어주는게 멀티쓰레드 환경에서 안전함
     */
    private fun validateDuplicateMember(member: Member) {
        if (memberRepository.findByName(member.name).isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }

    // 회원 전체 조회
    fun findMembers(): List<Member> = memberRepository.findAll()

    // 회원 한명 조회
    fun findOne(id: Long) = memberRepository.findOne(id)
}