package net.aquadc.properties.fx

import javafx.scene.Node
import net.aquadc.properties.Property

/**
 * Binds [Node.visibleProperty] to [visibleProperty].
 */
fun Node.bindVisibilitySoftlyTo(visibleProperty: Property<Boolean>) {
    visibleProperty().bindTo(visibleProperty)
}

/**
 * Binds [Node.visibleProperty] and [Node.managedProperty] to [visibleProperty].
 */
fun Node.bindVisibilityHardlyTo(visibleProperty: Property<Boolean>) {
    // As suggested in https://stackoverflow.com/a/28559958/3050249
    managedProperty().bindTo(visibleProperty)
    visibleProperty().bindTo(visibleProperty)
}
