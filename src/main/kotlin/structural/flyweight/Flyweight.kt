package structural.flyweight

import util.trace

data class PieceType(
    val type: String,
    val color: String,
) {
    fun move(position: String) = "$this to $position"
    override fun toString() = "$color $type"
}

class Piece(
    val pieceType: PieceType,
    position: String,
) {
    var position = position
        private set

    fun move(newPosition: String) = pieceType.move(newPosition)
        .also { position = newPosition }
}

object PieceFactory {
    private val pieceTypesInGame = mutableListOf<PieceType>()

    fun getPieceType(type: String, color: String): PieceType {
        val result = pieceTypesInGame.find { it.type == type && it.color == color }
        return result ?: PieceType(type, color)
            .also { pieceTypesInGame.add(it) }
    }
}

class Chessboard {
    private val piecesInGame = mutableListOf<Piece>()

    fun addPiece(
        type: String,
        color: String,
        position: String,
    ) {
        val pieceType = PieceFactory.getPieceType(type, color)
        piecesInGame.add(
            Piece(pieceType, position)
        )
    }

    fun movePiece(
        initialPosition: String,
        newPosition: String,
    ): String {
        val piece = piecesInGame.find { it.position == initialPosition }
        return piece?.move(newPosition) ?: "There is no such piece on the board"
    }

    override fun toString(): String {
        val piecePositions = mutableListOf<String>()
        piecesInGame.forEach { piecePositions += "${it.pieceType} on ${it.position}" }
        return piecePositions.joinToString()
    }
}

fun main() {
    val chessboard = Chessboard()
    chessboard.addPiece(type = "King", color = "White", position = "G1")
    chessboard.addPiece(type = "King", color = "Black", position = "H5")
    trace("Initial board: $chessboard")

    chessboard.movePiece("G1", "F1")
    trace("Final board: $chessboard")

    trace eq """
        Initial board: White King on G1, Black King on H5
        Final board: White King on F1, Black King on H5
    """
}

