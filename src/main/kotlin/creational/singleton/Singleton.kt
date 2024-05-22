package creational.singleton

import util.trace

class NotesFile private constructor() {
    val entries = mutableListOf("Initial note")

    companion object {
        private var notesFile: NotesFile? = null

        fun getFile(): NotesFile =
            synchronized(this) {
                notesFile ?: NotesFile().also { notesFile = it }
            }
    }
}

fun main() {
    val accessPoint1 = NotesFile.getFile()
    trace("AP1 - ${accessPoint1.entries.joinToString()}")

    val accessPoint2 = NotesFile.getFile()
    accessPoint2.entries.add("Added from second access point")
    trace("AP2 - ${accessPoint2.entries.joinToString()}")

    trace eq """
        AP1 - Initial note
        AP2 - Initial note, Added from second access point
    """
}