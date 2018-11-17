package net.coplanar.rougeTwo.actions

import net.coplanar.rougeTwo.Entity
import net.coplanar.rougeTwo.Map
import net.coplanar.rougeTwo.Message
import net.coplanar.rougeTwo.controllers.GameController
import java.awt.Point

class DeathAction : Action {


    constructor() : super() {

    }


    override fun perform(gameControl: GameController, actor: Entity) {

        super.perform(gameControl, actor);
        val map = gameControl.model.map
        map.entities.remove(actor);
            gameControl.view.removeComponent(actor);
            gameControl.displayMessage(Message().appendEntityName(actor).append(" has died."));

    }
}