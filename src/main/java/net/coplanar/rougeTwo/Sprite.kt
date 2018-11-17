package net.coplanar.rougeTwo

import com.valkryst.VTerminal.misc.ColorFunctions
import java.awt.Color


enum class Sprite (character: Char, backgroundColor: Color?, foregroundColor: Color?) {

    UNKNOWN('?', Color.BLACK, Color.RED),
    DARKNESS('█', Color.BLACK, Color.BLACK),
    DIRT('▒', Color(0x452F09), Color(0x372507)),
    GRASS('▒', Color(0x3D4509), Color(0x303707)),
    WALL('#', Color(0x494949), Color(0x3C3C3C)),

    PLAYER('@', Color(0, 0, 0, 0), Color.GREEN),
    ENEMY('E', Color(0, 0, 0, 0), Color.RED);


    val character: Char

    val backgroundColor: Color

    val foregroundColor: Color

    val darkBackgroundColor: Color

    val darkForegroundColor: Color

    init {
        this.character = character

        if (backgroundColor == null) {
            this.backgroundColor = Color.MAGENTA
        } else {
            this.backgroundColor = backgroundColor
        }

        if (foregroundColor == null) {
            this.foregroundColor = Color.MAGENTA
        } else {
            this.foregroundColor = foregroundColor
        }

        darkBackgroundColor = ColorFunctions.shade(this.backgroundColor, 0.5)
        darkForegroundColor = ColorFunctions.shade(this.foregroundColor, 0.5)
    }
}