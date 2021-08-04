package jpabook.jpashop.domain.base

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate


@MappedSuperclass
abstract class BaseEntity {

    abstract val id: Long?

    @Column(updatable = false)
    protected lateinit var createAt: LocalDateTime

    protected lateinit var updateAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        LocalDateTime.now().let {
            createAt = it
            updateAt = it
        }
    }

    @PreUpdate
    fun preUpdate() {
        updateAt = LocalDateTime.now()
    }
}