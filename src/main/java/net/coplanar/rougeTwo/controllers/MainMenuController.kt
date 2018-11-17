package net.coplanar.rougeTwo.controllers

import com.valkryst.VTerminal.Screen
import net.coplanar.rougeTwo.Controller

class MainMenuController(screen: Screen) : Controller<MainMenuView, MainMenuModel> (MainMenuView(screen), MainMenuModel()) {

    init {
        initializeEventHandlers(screen)
        screen.addComponent(super.view)
        screen.draw()
    }

    fun initializeEventHandlers(screen: Screen) {

        super.view.button_new.setOnClickFunction {
            super.removeFromScreen(screen)
            val controller = GameController(screen)
            controller.addToScreen(screen)
        }

        super.view.button_exit.setOnClickFunction { System.exit(0) }
    }
}