package data.itemsets

import character.*
import data.model.Item
import data.model.ItemSet
import sim.Event
import sim.SimParticipant

class Deathmantle : ItemSet() {
    companion object {
        const val TWO_SET_BUFF_NAME = "Deathmantle (2 set)"
        const val FOUR_SET_BUFF_NAME = "Deathmantle (4 set)"

        fun twoSetBonusDamagePerCP(): Double {
            return 40.0
        }
    }

    override val id: Int = 622

    val twoBuff = object : Buff() {
        override val name: String = TWO_SET_BUFF_NAME
        override val durationMs: Int = -1
    }

    val fourSetAbility = object : Ability() {
        override val name: String = Companion.FOUR_SET_BUFF_NAME
    }

    fun noCostConsumeProc(buff: Buff): Proc {
        return object : Proc() {
            override val triggers: List<Trigger> = listOf(
                Trigger.ROGUE_CAST_FINISHER
            )
            override val type: Type = Type.STATIC

            // TODO: is this also consumed on misses/dodges/parry etc? otherwise we have to check here for a hit
            // if it does consume on a miss/etc. we need to check interactions with the "Quick Recovery" talent.
            override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
                // Remove the crit buff
                sp.consumeBuff(buff)

                // Refund the resource cost of the triggering ability
                if(ability != null) {
                    sp.addResource(ability.resourceCost(sp).toInt(), ability.resourceType(sp), fourSetAbility)
                }
            }
        }
    }

    val freeFinisherBuff = object : Buff() {
        override val name: String = "Deathmantle (free finisher)"
        override val durationMs: Int = -1
        override val hidden: Boolean = true

        val proc = noCostConsumeProc(this)
        override fun procs(sp: SimParticipant): List<Proc> = listOf(proc)
    }

    val fourBuff = object : Buff() {
        override val name: String = FOUR_SET_BUFF_NAME
        override val durationMs: Int = -1
        override val hidden: Boolean = true

        val proc = object : Proc() {
            override val triggers: List<Trigger> = listOf(
                Trigger.PHYSICAL_DAMAGE
            )
            override val type: Type = Type.PPM
            override val ppm: Double = 1.0

            // TODO: does this also happen on misses/dodges/parry etc? otherwise we have to check here for a hit
            override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
                sp.addBuff(freeFinisherBuff)
            }
        }

        override fun procs(sp: SimParticipant): List<Proc> = listOf(proc)
    }

    override val bonuses: List<Bonus> = listOf(
        Bonus(id, 2, twoBuff),
        Bonus(id, 4, fourBuff)
    )
}
