package net.coplanar.rougeTwo.controllers

import com.valkryst.VTerminal.Screen
import com.sun.awt.SecurityWarning.setPosition
import com.sun.awt.SecurityWarning.setPosition
import com.valkryst.VTerminal.builder.TextAreaBuilder
import com.valkryst.VTerminal.component.Layer
import com.valkryst.VTerminal.component.TextArea
import com.valkryst.VTerminal.printer.RectanglePrinter
import net.coplanar.rougeTwo.*
import net.coplanar.rougeTwo.inventory.Equipment
import net.coplanar.rougeTwo.inventory.EquipmentSlot
import java.awt.Dimension
import java.awt.Point

class GameView(screen:Screen) : View(screen) {

    var messageBox: TextArea
    var playerInfoView: Layer? = null
    var targetInfoView: Layer? = null

    init {
        val builder = TextAreaBuilder()
        builder.setPosition(0, 40)
        builder.setWidth(80)
        builder.setHeight(5)
        builder.setEditable(false)

        messageBox = builder.build()
        this.addComponent(messageBox)
    }

    fun addModelComponents(model: GameModel) {
        val map = model.map
        val player = model.player
        val enemy = Entity(Sprite.ENEMY, Point(15,15), "Kobold")

        displayPlayerInformation(player);
        displayTargetInformation(enemy);

        this.addComponent(map)
        this.addComponent(player)
        player.lineOfSight.showLOSOnMap(map)

        this.addComponent(enemy)
        map.entities.add(enemy)

        // Add Equipment to Player
        val sword = Equipment(Sprite.UNKNOWN, "Sword", "A Sword", HashMap(), EquipmentSlot.MAIN_HAND);
        sword.addStat( BoundStat("Damage", 1, 10));
        player.inventory.equip(sword);

        // Add Armor to Target
        val shield =  Equipment(Sprite.UNKNOWN, "Shield", "A Shield", HashMap(), EquipmentSlot.OFF_HAND);
        shield.addStat( Stat("Armor", 3));
        enemy.inventory.equip(shield);

        model.map.updateLayerTiles();
    }

   fun displayPlayerInformation(player: Player) {
       val layer = Entity.getInfoPanel(player)
       layer.getTiles().setPosition(80, 0)
       playerInfoView = layer
       super.addComponent(playerInfoView)
   }

    fun displayTargetInformation(target: Entity?) {
        var layer = target?.let {
            Entity.getInfoPanel(it)
        } ?: Layer(Dimension(40,8)).also { newLayer ->
            var rectanglePrinter =  RectanglePrinter()
            rectanglePrinter.setWidth(40)
            rectanglePrinter.setHeight(8)
            rectanglePrinter.title = "No Target"
            rectanglePrinter.print(newLayer.getTiles(), Point(0, 0))
        }

        layer.tiles.setPosition(80, 8)
        targetInfoView.let { super.removeComponent(it) }
        targetInfoView = layer
        super.addComponent(targetInfoView)

    }



}