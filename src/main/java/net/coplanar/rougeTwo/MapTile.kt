package net.coplanar.rougeTwo

class MapTile {

    var sprite = Sprite.GRASS
    var movmentCost = 1
    var solid = true
    var visited = false
    var visible = false

    constructor(sprite: Sprite?) {
        this.sprite = sprite?: Sprite.UNKNOWN
    }
}