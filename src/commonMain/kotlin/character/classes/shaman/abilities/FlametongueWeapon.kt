package character.classes.shaman.abilities

import character.Ability
import character.Proc
import character.classes.shaman.talents.ElementalWeapons
import data.Constants
import data.model.Item
import mechanics.Melee
import mechanics.Spell
import sim.Event
import sim.EventResult
import sim.EventType
import sim.SimParticipant

class FlametongueWeapon(override val name: String, val item: Item) : Ability() {
    companion object {
        const val name = "Flametongue Weapon"
    }

    override val id: Int = 25489
    override fun gcdMs(sp: SimParticipant): Int = 0

    override fun available(sp: SimParticipant): Boolean {
        return if(Melee.isOffhand(sp, item)) { sp.isDualWielding() } else true
    }

    // Per internet anedcodes, this gets 10% of spell power
    val spCoeff = 0.10
    val baseDamage = 40.35
    override fun cast(sp: SimParticipant) {
        val elementalWeapons = sp.character.klass.talents[ElementalWeapons.name] as ElementalWeapons?
        val mod = elementalWeapons?.flametongueDamageMultiplier() ?: 1.0

        val school = Constants.DamageType.FIRE
        // TODO: Weapon speed scaling mechanics unconfirmed
        //       Current formula matches testing on pservers
        val speedBasedDamage = baseDamage * item.speed / 1000.0 * mod
        val damageRoll = Spell.baseDamageRollSingle(sp, speedBasedDamage, school, spCoeff)
        val result = Spell.attackRoll(sp, damageRoll, school)

        val event = Event(
            eventType = EventType.DAMAGE,
            damageType = school,
            ability = this,
            amount = result.first,
            result = result.second,
        )
        sp.logEvent(event)

        // Proc anything that can proc off Fire damage
        val triggerTypes = when (result.second) {
            EventResult.HIT -> listOf(Proc.Trigger.FIRE_DAMAGE_NON_PERIODIC)
            EventResult.CRIT -> listOf(Proc.Trigger.FIRE_DAMAGE_NON_PERIODIC)
            EventResult.PARTIAL_RESIST_HIT -> listOf(Proc.Trigger.FIRE_DAMAGE_NON_PERIODIC)
            EventResult.PARTIAL_RESIST_CRIT -> listOf(Proc.Trigger.FIRE_DAMAGE_NON_PERIODIC)
            else -> null
        }

        if (triggerTypes != null) {
            sp.fireProc(triggerTypes, listOf(), this, event)
        }
    }
}
