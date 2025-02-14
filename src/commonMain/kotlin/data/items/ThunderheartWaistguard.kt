package `data`.items

import `data`.Constants
import `data`.itemsets.ItemSets
import `data`.model.Color
import `data`.model.Item
import `data`.model.ItemSet
import `data`.model.Socket
import `data`.model.SocketBonus
import `data`.socketbonus.SocketBonuses
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
public class ThunderheartWaistguard : Item() {
  public override var isAutoGenerated: Boolean = true

  public override var id: Int = 34556

  public override var name: String = "Thunderheart Waistguard"

  public override var itemLevel: Int = 154

  public override var quality: Int = 4

  public override var icon: String = "inv_belt_24.jpg"

  public override var inventorySlot: Int = 6

  public override var itemSet: ItemSet? = ItemSets.byId(676)

  public override var itemClass: Constants.ItemClass? = Constants.ItemClass.ARMOR

  public override var itemSubclass: Constants.ItemSubclass? = Constants.ItemSubclass.LEATHER

  public override var allowableClasses: Array<Constants.AllowableClass>? = arrayOf(
      Constants.AllowableClass.DRUID
      )

  public override var minDmg: Double = 0.0

  public override var maxDmg: Double = 0.0

  public override var speed: Double = 0.0

  public override var stats: Stats = Stats(
      strength = 38,
      agility = 40,
      stamina = 33,
      intellect = 20,
      armor = 342,
      physicalHitRating = 23.0
      )

  public override var sockets: Array<Socket> = arrayOf(
      Socket(Color.RED)
      )

  public override var socketBonus: SocketBonus? = SocketBonuses.byId(2925)

  public override var phase: Int = 5

  public override val buffs: List<Buff> by lazy {
        listOf()}

}
