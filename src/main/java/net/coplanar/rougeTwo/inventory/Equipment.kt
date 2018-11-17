package net.coplanar.rougeTwo.inventory

import com.valkryst.VDice.DiceRoller
import net.coplanar.rougeTwo.BoundStat
import net.coplanar.rougeTwo.Item
import net.coplanar.rougeTwo.Stat
import net.coplanar.rougeTwo.Sprite

class Equipment(sprite: Sprite, name: String, description: String, stats: HashMap<String, Stat> = HashMap(), slot: EquipmentSlot = EquipmentSlot.UNKNOWN): Item(sprite, name, description, stats) {
    /** The slot in which this item can be equipped.  */
    val slot: EquipmentSlot

    init {
        this.slot = slot
    }

    fun rollDamage() : Int {
        (getStat("Damage") as BoundStat).also { damageStat ->
            val diceRoller = DiceRoller()
            diceRoller.addDice(damageStat.maxValue - damageStat.minValue, 1);
            return diceRoller.roll() + damageStat.minValue
        }
        return 0
    }

   fun getArmor() : Int {
       return super.getStat("Armor")?.value ?: 0
   }


}