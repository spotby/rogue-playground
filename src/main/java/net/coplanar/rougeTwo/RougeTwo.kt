package net.coplanar.rougeTwo

import com.valkryst.VTerminal.Screen
import javax.swing.JFrame
import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.font.FontLoader
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import com.valkryst.VTerminal.font.FontLoader.loadFontFromJar
import net.coplanar.rougeTwo.controllers.MainMenuController


fun main(args : Array<String>) {

    //System.setProperty("sun.java2d.trace", "log,verbose");
    //System.setProperty("sun.java2d.opengl", "true");
//    System.setProperty("sun.java2d.d3d", "false")
//    System.setProperty("sun.java2d.opengl", "false")

    val font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1.toDouble())
    val screen = Screen(120, 45, font)
    screen.addCanvasToFrame()


    val controller = MainMenuController(screen);
    controller.addToScreen(screen);
    screen.draw()

}