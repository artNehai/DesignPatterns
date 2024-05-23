package structural.composite

import util.trace

interface DirectoryComponent {
    fun getSizeKb(): Int
}

class File(
    val name: String,
    val content: String,
) : DirectoryComponent {

    override fun getSizeKb() = (content.count { it == ' ' } + 1) * 10

    override fun toString() = "$name file"
}

class Folder(
    val name: String,
) : DirectoryComponent {

    private val content = mutableListOf<DirectoryComponent>()

    fun add(component: DirectoryComponent) =
        content.add(component)

    fun remove(component: DirectoryComponent) =
        content.remove(component)

    fun getContent(): String = content.joinToString()

    override fun getSizeKb() = content.fold(initial = 0) { acc, directoryComponent ->
        acc + directoryComponent.getSizeKb()
    }

    override fun toString() = "$name folder"
}

fun main() {
    val rootFolder = Folder(name = "Root")
    val innerFolder = Folder(name = "Inner")
    val clientsFile = File(name = "Clients.txt", content = "Larry, Tyson")
    val employeesFile = File(name = "Employees.txt", content = "Mr.Bean")

    rootFolder.add(innerFolder)
    rootFolder.add(clientsFile)
    innerFolder.add(employeesFile)

    trace("$rootFolder (${rootFolder.getSizeKb()}Kb): ${rootFolder.getContent()}")
    trace("$clientsFile: ${clientsFile.content}")
    trace("$innerFolder (${innerFolder.getSizeKb()}Kb): ${innerFolder.getContent()}")
    trace("$employeesFile: ${employeesFile.content}")

    trace eq """
        Root folder (30Kb): Inner folder, Clients.txt file
        Clients.txt file: Larry, Tyson
        Inner folder (10Kb): Employees.txt file
        Employees.txt file: Mr.Bean
    """
}