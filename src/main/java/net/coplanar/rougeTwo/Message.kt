package net.coplanar.rougeTwo

import java.awt.Color
import com.valkryst.VTerminal.Tile



open class Message() {

    companion object {
        public val DEFAULT_BACKGROUND_COLOR = Color(-0x716662)
        public val DEFAULT_FOREGROUND_COLOR = Color(-0x972f01)
    }

    // TODO get rid of ? here and make sure tiles can never be null
    var message: Array<Tile?>

    init {
        message = prepareString("")
    }

    constructor(text: String): this() {
        message = prepareString(text)
    }

    constructor(text: String,  color: Color): this(text)  {
      message.forEach { tile -> tile!!.foregroundColor = color }
    }


    // TODO rewrite this to a map
    fun prepareString(length: Int): Array<Tile?> {
        val tiles = arrayOfNulls<Tile>(length)

        for (i in tiles.indices) {
            tiles[i] = Tile(' ')
            tiles[i]!!.setBackgroundColor(DEFAULT_BACKGROUND_COLOR)
            tiles[i]!!.setForegroundColor(DEFAULT_FOREGROUND_COLOR)
        }

        return tiles
    }

    // TODO rewrite this to a map
    fun prepareString(text: String?): Array<Tile?> {
        if (text == null) {
            return arrayOfNulls(0)
        }

        val tiles = arrayOfNulls<Tile>(text.length)

        for (i in 0 until text.length) {
            tiles[i] = Tile(text[i])
            tiles[i]!!.setCharacter(text[i])
            tiles[i]!!.setBackgroundColor(DEFAULT_BACKGROUND_COLOR)
            tiles[i]!!.setForegroundColor(DEFAULT_FOREGROUND_COLOR)
        }

        return tiles
    }

    fun append(text: String?): Message {
        if (text != null) {
            append(prepareString(text))
        }

        return this
    }

    // TODO refactor this to be more kotliny
    fun append(tiles: Array<Tile?>): Message {

        val newMessage = arrayOfNulls<Tile>(message.size + tiles.size)

        for (i in 0 until message.size) {
            newMessage[i] = Tile(' ')
            newMessage[i]!!.copy(message[i])
        }

        for (i in message.size until newMessage.size) {
            newMessage[i] = Tile(' ')
            newMessage[i]!!.copy(tiles[i - message.size])
        }

        message = newMessage

        return this
    }

    fun appendEntityName(entity: Entity) : Message {
        val color = if (entity == null)  Color.MAGENTA else entity.sprite.foregroundColor
        val name = if (entity == null)  "null" else entity.name
        val nameMessage = Message(name, color)
        return  this.append(nameMessage.message)

    }
}
