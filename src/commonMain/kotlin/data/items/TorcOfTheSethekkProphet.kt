package `data`.items

import `data`.Constants
import `data`.buffs.Buffs
import `data`.model.Item
import `data`.model.ItemSet
import `data`.model.Socket
import `data`.model.SocketBonus
import character.Buff
import character.Stats
import kotlin.Array
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlin.js.JsExport

@JsExport
public class TorcOfTheSethekkProphet : Item() {
  public override var isAutoGenerated: Boolean = true

  public override var id: Int = 29333

  public override var name: String = "Torc of the Sethekk Prophet"

  public override var itemLevel: Int = 115

  public override var quality: Int = 3

  public override var icon: String = "inv_jewelry_necklace_29naxxramas.jpg"

  public override var inventorySlot: Int = 2

  public override var itemSet: ItemSet? = null

  public override var itemClass: Constants.ItemClass? = Constants.ItemClass.ARMOR

  public override var itemSubclass: Constants.ItemSubclass? = Constants.ItemSubclass.MISC

  public override var allowableClasses: Array<Constants.AllowableClass>? = null

  public override var minDmg: Double = 0.0

  public override var maxDmg: Double = 0.0

  public override var speed: Double = 0.0

  public override var stats: Stats = Stats(
      intellect = 18,
      spellCritRating = 21.0
      )

  public override var sockets: Array<Socket> = arrayOf()

  public override var socketBonus: SocketBonus? = null

  public override var phase: Int = 1

  public override val buffs: List<Buff> by lazy {
        listOfNotNull(
        Buffs.byIdOrName(14254, "Increase Spell Dam 19", this)
        )}

}
