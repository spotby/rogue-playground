package net.coplanar.rougeTwo.actions

import net.coplanar.rougeTwo.*
import net.coplanar.rougeTwo.Map
import java.awt.Point
import net.coplanar.rougeTwo.controllers.GameController


public open class MoveAction : Action {

    val originalPosition: Point
    var newPosition: Point
    val dx: Int
    val dy: Int

    constructor(position: Point, dx: Int, dy: Int) : super() {
        this.originalPosition = position
        this.newPosition = Point(dx + position.x, dy + position.y)
        this.dx = dx
        this.dy = dy
    }

    override fun perform(gameControl: GameController, actor: Entity) {
        // Attack any enemies at new location:
        val map =  gameControl.model.map

        try {
            val target = map.entities
                    .first { e -> e.position == newPosition }
            println("entity is at newPosition, fight!")
            //if (actor is Player) {
            AttackAction(target).perform(gameControl,actor)
        } catch (ignored: Exception) {}

        if (map.isPositionFree(newPosition)) {
            super.perform( gameControl, actor)
            actor.position = newPosition
            if (actor is Player) {
                gameControl.displayMessage(Message("${actor.name} moves to (${newPosition.x},${newPosition.y})"))
                val los = actor.lineOfSight
                los.hideLOSOnMap(map);
                los.move(dx, dy);
                los.showLOSOnMap(map);
            } else {
                actor.lineOfSight.move(dx, dy)
            }

        }
    }
}