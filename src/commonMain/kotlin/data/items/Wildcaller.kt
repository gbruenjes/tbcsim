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
public class Wildcaller : Item() {
  public override var isAutoGenerated: Boolean = true

  public override var id: Int = 31009

  public override var name: String = "Wildcaller"

  public override var itemLevel: Int = 109

  public override var quality: Int = 3

  public override var icon: String = "inv_staff_51.jpg"

  public override var inventorySlot: Int = 17

  public override var itemSet: ItemSet? = null

  public override var itemClass: Constants.ItemClass? = Constants.ItemClass.WEAPON

  public override var itemSubclass: Constants.ItemSubclass? = Constants.ItemSubclass.STAFF

  public override var allowableClasses: Array<Constants.AllowableClass>? = null

  public override var minDmg: Double = 122.76000213623047

  public override var maxDmg: Double = 221.75999450683594

  public override var speed: Double = 2800.0

  public override var stats: Stats = Stats(
      strength = 45,
      agility = 24,
      expertiseRating = 20.0
      )

  public override var sockets: Array<Socket> = arrayOf()

  public override var socketBonus: SocketBonus? = null

  public override var phase: Int = 1

  public override val buffs: List<Buff> by lazy {
        listOfNotNull(
        Buffs.byIdOrName(44906, "Attack Power - Feral (+0457)", this)
        )}

}
