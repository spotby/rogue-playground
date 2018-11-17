package net.coplanar.rougeTwo.actions

import net.coplanar.rougeTwo.Entity
import net.coplanar.rougeTwo.Map
import net.coplanar.rougeTwo.controllers.GameController


open class Action {
    //TODO should this be lambdas instead?
    var runnables: ArrayList<Runnable> = ArrayList()

    open fun perform(gameControl: GameController, entity: Entity) {
        runnables.forEach(Runnable::run)
    }

    fun addRunnable(runnable: Runnable) {
        runnables.add(runnable)
    }

    fun removeRunnable(runnable: Runnable) {
        runnables.remove(runnable)
    }

    fun removeAllRunnables() {
        runnables.clear()
    }

}