package jpabook.jpashop.extensions

fun requireNotBlank(string: String?): String =
    string?.takeIf { it.isNotBlank() }
        ?: throw IllegalStateException("Required value was blank.")

fun require(string: String?): String =
    string?.takeIf { it.isNotBlank() }
        ?: throw IllegalStateException("Required value was blank.")
