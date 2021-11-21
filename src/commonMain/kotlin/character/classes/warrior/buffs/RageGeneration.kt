package character.classes.warrior.buffs

import character.Ability
import character.Buff
import character.Proc
import character.Resource
import character.classes.warrior.talents.EndlessRage
import data.model.Item
import mechanics.Melee
import sim.Event
import sim.EventType
import sim.SimIteration
import sim.SimParticipant

class RageGeneration : Buff() {
    override val name: String = "Rage Generation"
    override val durationMs: Int = -1
    override val hidden: Boolean = true

    val hitAbility = object : Ability() {
        override val name: String = "Melee Hit"
    }

    val critAbility = object : Ability() {
        override val name: String = "Melee Crit"
    }

    val rageConversionFactor = 274.7
    fun damageToRage(sp: SimParticipant, damage: Double, item: Item, weaponFactor: Double): Int {
        val endlessRage = sp.character.klass.talents[EndlessRage.name]?.currentRank ?: 0 > 0
        val multiplier = if(endlessRage) { 1.25 } else 1.0

        return (((damage / rageConversionFactor * 7.5) + (item.speed / 1000.0 * weaponFactor)) / 2.0 * multiplier).toInt()
    }

    val procHit = object : Proc() {
        override val triggers: List<Trigger> = listOf(
            Trigger.MELEE_WHITE_HIT,
            Trigger.MELEE_AUTO_HIT,
            Trigger.MELEE_BLOCK,
            Trigger.MELEE_GLANCE,
        )
        override val type: Type = Type.STATIC

        override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
            val item = items?.get(0) ?: return
            val isOffhand = Melee.isOffhand(sp, item)

            if(event?.eventType == EventType.DAMAGE && event.isWhiteDamage) {
                val damage = event.amount
                val rage = if (isOffhand) {
                    damageToRage(sp, damage, item, 1.75)
                } else {
                    damageToRage(sp, damage, item, 3.5)
                }

                sp.addResource(rage, Resource.Type.RAGE, hitAbility)
            }
        }
    }

    val procCrit = object : Proc() {
        override val triggers: List<Trigger> = listOf(
            Trigger.MELEE_WHITE_CRIT,
            Trigger.MELEE_AUTO_CRIT
        )
        override val type: Type = Type.STATIC

        override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
            val item = items?.get(0) ?: return
            val isOffhand = Melee.isOffhand(sp, item)

            if(event?.eventType == EventType.DAMAGE && event.isWhiteDamage) {
                val damage = event.amount
                val rage = if (isOffhand) {
                    damageToRage(sp, damage, item, 3.5)
                } else {
                    damageToRage(sp, damage, item, 7.0)
                }

                sp.addResource(rage, Resource.Type.RAGE, critAbility)
            }
        }
    }

    // TODO: Refund rage for mitigated yellow attacks, once I figure out how TBC does that
    //       May need to be implemented per-ability, if it's the same as Classic
    override fun procs(sp: SimParticipant): List<Proc> = listOf(procHit, procCrit)
}
