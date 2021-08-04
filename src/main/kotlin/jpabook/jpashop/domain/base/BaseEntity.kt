package jpabook.jpashop.domain.base

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    abstract val id: Long?

    @CreatedDate
    @Column(updatable = false)
    protected val createAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    protected var updateAt: LocalDateTime = LocalDateTime.now()
}