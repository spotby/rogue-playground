package net.coplanar.rougeTwo

import sun.jvm.hotspot.tools.ClassLoaderStats

open class Item(sprite: Sprite = Sprite.UNKNOWN, name:String = "unknown", description:String = "", stats: HashMap<String,Stat> = HashMap())  {

    val sprite:Sprite
    val name:String
    val description:String
    var stats:HashMap<String,Stat>

    init{
        this.sprite = sprite
        this.name = name
        this.description = description
        this.stats = stats
    }

    fun addStat(stat:Stat) {
        stats.putIfAbsent(stat.name, stat)
    }

    fun removeStat(name: String) {
        stats.remove(name)
    }

    fun getStat(name:String):Stat? {
        return stats.get(name)
    }

}