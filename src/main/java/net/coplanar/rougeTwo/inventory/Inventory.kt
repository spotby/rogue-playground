package net.coplanar.rougeTwo.inventory

import net.coplanar.rougeTwo.Item
import java.util.HashMap
import java.util.Arrays

class Inventory(size: Int) {

    // The Equipped Items
    val equipment = HashMap<EquipmentSlot, Equipment>()

    // The non-equipped items.
    var items : Array<Item?>

    init{
        this.items = Array(size, {i -> null })
    }

    fun equip(item: Equipment) {
        val slot = item.slot

        if (put(equipment[slot])) {
            equipment[slot] = item
        }
    }

    fun unequip(slot: EquipmentSlot) {
        if (put(equipment[slot])) {
            equipment.remove(slot)
        }
    }

    // TODO refactor
    fun put(item: Item?): Boolean {
        if (item == null) {
            return true
        }

        for (i in 0 until items.size) {
            if (items[i] == null) {
                items[i] = item
                return true
            }
        }

        return false
    }

    fun remove(item: Item) {
        this.items = this.items.filter { i -> i == null || i!!.equals(item) }.toTypedArray()
    }

    /**
     * Removes an item at a specific index.
     *
     * @param index
     * The index.
     */
    fun remove(index: Int) {
        if (index < 0 || index >= items.size) {
            return
        }

        items[index] = null
    }

    /** Removes all equipment and items from the inventory.  */
    fun clear() {
        equipment.clear()
        Arrays.fill(items, null)
    }

    fun getEquipment(slot: EquipmentSlot): Equipment? {
        return equipment[slot]
    }

    fun getItem(index: Int): Item? {
        return if (index < 0 || index >= items.size) {
            null
        } else items[index]

    }

    fun getSize(): Int {
        return items.size
    }

}