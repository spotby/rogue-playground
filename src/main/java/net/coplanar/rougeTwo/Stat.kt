package net.coplanar.rougeTwo

import com.valkryst.VTerminal.builder.LabelBuilder
import com.valkryst.VTerminal.component.Label
import java.util.ArrayList


open class Stat (name: String, value:Int) {

    open var value = 0
    set(newValue) {
        field = newValue
        runnables.forEach { run -> run() }
    }
    val name: String

    /** The runnable functions to run whenever the value is changed.  */
    val runnables = ArrayList<() -> Unit>()

    init {
        this.name = name
        this.value = value
    }

    fun getLabel() : Label {
        val labelBuilder = LabelBuilder()
        labelBuilder.setText(name + ": " + value)
        val label = labelBuilder.build()
        label.setId(name)
        return label
    }

}