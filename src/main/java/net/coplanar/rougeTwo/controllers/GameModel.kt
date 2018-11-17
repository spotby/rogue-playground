package net.coplanar.rougeTwo.controllers

import com.valkryst.VTerminal.Screen
import net.coplanar.rougeTwo.Map
import net.coplanar.rougeTwo.Model
import net.coplanar.rougeTwo.Player
import java.awt.Point
import net.coplanar.rougeTwo.Room
import java.awt.Dimension



class GameModel(screen: Screen) : Model() {
    val map = Map()
    val player: Player
    val screen = screen

    init {
        val room = Room(Point(10, 10), Dimension(10, 10))
        room.carve(map)
        player = Player(Point(12,12), "Keene")
        map.entities.add(player)

    }
}