package net.coplanar.rougeTwo

import com.valkryst.VTerminal.builder.LabelBuilder
import com.valkryst.VTerminal.component.Label

class BoundStat(name: String, minValue:Int, maxValue:Int, value:Int) : Stat(name,value) {

    var minValue = 0
    var maxValue = 0

     override var value: Int
         get() = super.value
         set(value) {

            if (value > maxValue) {
                super.value =  maxValue
                return
            }

            if (value < minValue) {
                super.value = minValue
                return;
            }

            super.value = value
        }

    init {
        this.maxValue = if (maxValue < minValue) minValue else maxValue
        this.minValue = minValue
        this.value = value
    }

    constructor(name: String, minValue: Int, maxValue: Int) : this(name, minValue,maxValue,maxValue)

    fun getBoundLabel(): Label {
        val labelBuilder = LabelBuilder()
        labelBuilder.text = "${super.name }:${super.value}/${maxValue}"
        val label = labelBuilder.build()
        label.id = super.name
        return label
    }


}