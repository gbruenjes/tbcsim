package data.itemscustom

import character.Stats
import data.Constants
import data.model.Item
import data.model.ItemSet
import data.model.Socket
import data.model.SocketBonus
import kotlin.js.JsExport

@JsExport
class LurkersCordOfShadowWrath : Item() {
    override var isAutoGenerated: Boolean = false
    override var id: Int = 30675
    override var name: String = "Lurker's Cord of Shadow Wrath"
    override var itemLevel: Int = 115
    override var quality: Int = 4
    override var icon: String = "inv_belt_03.jpg"
    override var inventorySlot: Int = 6
    override var itemSet: ItemSet? = null
    override var itemClass: Constants.ItemClass? = Constants.ItemClass.ARMOR
    override var itemSubclass: Constants.ItemSubclass? = Constants.ItemSubclass.CLOTH
    override var minDmg: Double = 0.0
    override var maxDmg: Double = 0.0
    override var speed: Double = 0.0
    override var stats: Stats = Stats(
        shadowDamage = 78,
    )
    override var sockets: Array<Socket> = arrayOf()
    override var socketBonus: SocketBonus? = null
    override var phase = 1
}
