package net.coplanar.rougeTwo.inventory

enum class EquipmentSlot {
    HEAD,
    NECK,
    BACK,
    SHOULDERS,
    CHEST,
    WRISTS,
    HANDS,
    WAIST,
    LEGS,
    FEET,
    MAIN_HAND,
    OFF_HAND,
    UNKNOWN;


    val humanName: String
        get() {
            var name = this.name
            name = name.toLowerCase()
            name = name.replace('_', ' ')
            name = name.substring(0, 1).toUpperCase() + name.substring(1)
            return name
        }
}