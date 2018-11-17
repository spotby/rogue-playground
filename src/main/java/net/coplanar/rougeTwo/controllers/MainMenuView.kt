package net.coplanar.rougeTwo.controllers

import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.component.Button
import net.coplanar.rougeTwo.View
import com.sun.awt.SecurityWarning.setPosition
import com.valkryst.VTerminal.builder.ButtonBuilder
import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.TileGrid
import com.valkryst.VTerminal.component.Layer
import java.io.IOException
import com.valkryst.VTerminal.printer.ImagePrinter
import java.awt.Color
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Dimension
import java.awt.Point


class MainMenuView(screen: Screen): View(screen) {

    lateinit var button_new : Button
    lateinit var button_exit : Button

    init {
        initComponents()
       // this.addComponent(loadLogo())
        this.addComponent(button_new)
        this.addComponent(button_exit)
    }

    fun initComponents() {
        val buttonBuilder = ButtonBuilder()
        buttonBuilder.setText("New Game")
        buttonBuilder.setPosition(54, 22)
        this.button_new = buttonBuilder.build()

        buttonBuilder.setText("Exit")
        buttonBuilder.setPosition(56, 24)
        this.button_exit = buttonBuilder.build()
    }

    private fun loadLogo(): Layer {
        val layer = Layer(Dimension(80, 16), Point(0, 0))
        this.addComponent(layer)


        val thread = Thread.currentThread()
        val classLoader = thread.contextClassLoader

        try {
            classLoader.getResourceAsStream("CityLogo.png").use { `is` ->
                val image = ImageIO.read(`is`)

                val printer = ImagePrinter(image)
                printer.printDetailed(layer.getTiles(), Point(0, 0))
            }
        } catch (e: IOException) {
            // todo Log the error message.

            // If we're unable to load the logo, then we'll set the layer's colors to something that stands out.
            val layerGrid = layer.getTiles()

            for (y in 0 until layerGrid.getHeight()) {
                for (x in 0 until layerGrid.getWidth()) {
                    val tile = layerGrid.getTileAt(x, y)
                    tile.setCharacter('?')
                    tile.setForegroundColor(Color.GREEN)
                    tile.setBackgroundColor(Color.MAGENTA)
                }
            }
        } catch (e: IllegalArgumentException) {
            val layerGrid = layer.getTiles()
            for (y in 0 until layerGrid.getHeight()) {
                for (x in 0 until layerGrid.getWidth()) {
                    val tile = layerGrid.getTileAt(x, y)
                    tile.setCharacter('?')
                    tile.setForegroundColor(Color.GREEN)
                    tile.setBackgroundColor(Color.MAGENTA)
                }
            }
        }

        return layer
    }
}