package net.coplanar.rougeTwo.controllers

import com.valkryst.VTerminal.Screen
import net.coplanar.rougeTwo.Controller
import net.coplanar.rougeTwo.Message

import java.awt.event.KeyListener
import java.awt.event.KeyEvent
import java.util.*
import kotlin.concurrent.timer


class GameController(screen:Screen) : Controller<GameView,GameModel> (GameView(screen), GameModel(screen)) {

    val screen = screen
    val timer:  Timer

    init {
        view.addModelComponents(model)
        initializeEventHandlers()
        timer = timer("game-loop",
                true,15,16)
        {
            val map = super.model.map
            for (entity in map.entities) {
                entity.performActions(map, this@GameController)
            }
            updateMapAndScreen()
        }
    }

    private fun updateMapAndScreen() {
        val map = super.model.map
        map.updateLayerTiles()
        screen.draw()
    }

    private fun initializeEventHandlers() {
        val player = super.model.player

        val keyListener = object : KeyListener {
            override fun keyTyped(e: KeyEvent) {}

            override fun keyPressed(e: KeyEvent) {}

            override fun keyReleased(e: KeyEvent) {
                when (e.getKeyCode()) {

                    KeyEvent.VK_W, KeyEvent.VK_UP -> player.move(0, -1)
                    KeyEvent.VK_S, KeyEvent.VK_DOWN -> player.move(0, 1)
                    KeyEvent.VK_A, KeyEvent.VK_LEFT -> player.move(-1, 0)
                    KeyEvent.VK_D, KeyEvent.VK_RIGHT -> player.move(1, 0)

                }
            }
        }

        super.model.eventListeners.add(keyListener)
    }


    fun displayMessage(message: Message) {
        if (message != null) {
            view.messageBox.appendText(message.message)
        }
    }

}