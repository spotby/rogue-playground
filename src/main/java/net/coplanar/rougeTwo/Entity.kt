package net.coplanar.rougeTwo

import com.valkryst.VTerminal.builder.LabelBuilder
import com.valkryst.VTerminal.component.Layer
import com.valkryst.VTerminal.printer.RectanglePrinter
import net.coplanar.rougeTwo.actions.Action
import net.coplanar.rougeTwo.actions.MoveAction
import net.coplanar.rougeTwo.controllers.GameController
import java.awt.Dimension
import java.awt.Point
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.HashMap
import net.coplanar.rougeTwo.inventory.Inventory




open public class Entity(sprite: Sprite, position: Point, name: String): Layer( Dimension(1, 1))
{
    private val actions = ConcurrentLinkedQueue<Action>()
    private val stats = HashMap<String, Stat>()
    val inventory = Inventory(26)
    var lineOfSight: LineOfSight

    var sprite: Sprite = Sprite.UNKNOWN
        set(sprite) {
            val tile = super.getTileAt(0,0)
            tile.character = sprite.character
            tile.foregroundColor = sprite.foregroundColor
            tile.backgroundColor = sprite.backgroundColor
            field = sprite
        }

    var name: String = ""

    var position
        get() = Point(super.getTiles().xPosition, super.getTiles().yPosition)
        set(pos) {
            super.getTiles().setXPosition(pos.x);
            super.getTiles().setYPosition(pos.y);
        }

    init {
        this.sprite = sprite
        this.name = name
        if (position.x < 0 || position.y < 0) {
            getTiles().setPosition(Point(0,0))
        } else {
            getTiles().setPosition(position)
        }
        val health = BoundStat("Health", 0, 100, 100)
        val level = BoundStat("Level", 1, 60, 1)
        val experience = BoundStat("Experience", 0, 100, 0)

        addStat(health)
        addStat(level)
        addStat(experience)

        lineOfSight = LineOfSight(4, position)
    }

    fun move(dx: Int, dy: Int) {
        actions.add(MoveAction(this.position, dx, dy))
    }

    fun addAction(action: Action?) {
        actions.add(action)
    }

    fun performActions(map: Map, controller: GameController) {
        for (action in actions) {
            action.perform(controller, this )
        }
        actions.clear()
    }

    fun addStat(stat: Stat) {
        stats.putIfAbsent(stat.name.toLowerCase(), stat)
    }

    fun removeStat(name: String) {
        stats.remove(name.toLowerCase())
    }

    fun getStat(name: String): Stat? {
        return stats[name.toLowerCase()]
    }

    fun getDamage() :Int {
        return inventory.equipment.values
                .map { e -> e.rollDamage() }
                .sum()
    }

    fun getArmor() :Int {
        return inventory.equipment.values
                .map { e -> e.getArmor() }
                .sum()
    }

    companion object {
        @JvmStatic
        fun getInfoPanel(entity: Entity): Layer {
            val layer = Layer(Dimension(40,8))
            val rectanglePrinter = RectanglePrinter()
            rectanglePrinter.setWidth(40)
            rectanglePrinter.setHeight(8)
            rectanglePrinter.title = entity.name
            rectanglePrinter.print(layer.tiles, Point(0,0))

            val nameTiles = layer.tiles.getRowSubset(0, 2, entity.name.length);
            nameTiles.forEach { tile -> tile.foregroundColor = entity.sprite.foregroundColor }

            val health = entity.getStat("Health") as BoundStat
            val xp = entity.getStat("Experience") as BoundStat
            val level = entity.getStat("Level") as BoundStat

            val add_level_func: () -> Unit = {
                val labelId = entity.name + "-" + level.name
                layer.getComponentsByID(labelId).forEach{layer.removeComponent(it)}
                val label = level.getLabel()
                label.id = labelId
                label.tiles.setPosition(1,1)
                layer.addComponent(label)
            }

            val add_xp_func: () -> Unit = {
                val labelId = entity.name + "-" + xp.name
                layer.getComponentsByID(labelId).forEach{layer.removeComponent(it)}
                val label = xp.getLabel()
                label.id = labelId
                label.tiles.setPosition(1,2)
                layer.addComponent(label)
            }

            val add_health_func: () -> Unit = {
                // println("refreshing health")
                val labelId = entity.name + "-" + health.name
                layer.getComponentsByID(labelId).forEach{layer.removeComponent(it)}
                var label = health.getLabel()
                if (health.value <= 0) {
                    val labelBuilder = LabelBuilder()
                    labelBuilder.text = "${health.name}:Deceased"
                    label = labelBuilder.build()
                }
                label.id = labelId
                label.tiles.setPosition(1,3)
                layer.addComponent(label)
            }
            level.runnables.add(add_level_func)
            xp.runnables.add(add_xp_func)
            health.runnables.add (add_health_func)

            // run each runnable the first time to populate the layer
            add_health_func.invoke()
            add_level_func.invoke()
            add_xp_func.invoke()
            return layer
        }

    }

}