package net.coplanar.rougeTwo

import com.valkryst.VTerminal.Screen

open class Controller<V : View, M : Model> (view: V,model: M) {

    val view : V
    val model: M

    init {
        this.model = model
        this.view = view
    }

    fun addToScreen(screen: Screen) {
        screen.addComponent(view)
        for (listener in model.eventListeners) {
            screen.addListener(listener)
        }
    }

    fun removeFromScreen(screen: Screen) {
        screen.removeComponent(view)
        for (listener in model.eventListeners) {
            screen.removeListener(listener)
        }
    }
}