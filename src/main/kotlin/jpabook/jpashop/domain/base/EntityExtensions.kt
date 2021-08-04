package jpabook.jpashop.domain.base

import javax.persistence.EntityManager


fun <T : BaseEntity> EntityManager.persistAndGetId(entity: T): Long {
    persist(entity)
    return requireNotNull(entity.id)
}