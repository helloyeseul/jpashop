package jpabook.jpashop.exception


class NotEnoughStockException @JvmOverloads constructor(
    s: String? = null,
    cause: Throwable? = null
) : RuntimeException(s, cause)
