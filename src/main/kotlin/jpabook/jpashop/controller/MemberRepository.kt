package jpabook.jpashop.controller

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository(
    @PersistenceContext
    private val em: EntityManager
) {

    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun find(id: Long): Member = em.find(Member::class.java, id)
}