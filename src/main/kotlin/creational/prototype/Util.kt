package creational.prototype

fun String.addIfPresent(extra: String?): String = this +
        if (extra != null) " with $extra"
        else ""