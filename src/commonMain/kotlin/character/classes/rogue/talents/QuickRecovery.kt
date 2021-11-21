package character.classes.rogue.talents

import character.*
import sim.SimParticipant
import sim.Event
import data.model.Item
import sim.EventResult

class QuickRecovery(currentRank: Int) : Talent(currentRank) {
    companion object {
        const val name = "Quick Recovery"
    }

    override val name: String = Companion.name
    override val maxRank: Int = 2

    val qrAbility = object : Ability() {
        override val name: String = Companion.name
    }

    fun finisherMissCostRefundFraction(): Double {
        return currentRank * 0.4
    }

    val buff = object : Buff() {
        override val name: String = "${Companion.name} (Talent)"
        override val durationMs: Int = -1
        override val hidden: Boolean = true

        val proc = object : Proc() {
            override val triggers: List<Trigger> = listOf(
                Trigger.ROGUE_CAST_FINISHER
            )
            override val type: Type = Type.STATIC

            override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
                if(event?.result == EventResult.MISS || event?.result == EventResult.DODGE) {
                    val cost = ability?.resourceCost(sp) ?: 0.0
                    sp.addResource((cost * finisherMissCostRefundFraction()).toInt(), Resource.Type.ENERGY, qrAbility)
                }
            }
        }

        override fun procs(sp: SimParticipant): List<Proc> = listOf(proc)
    }

    override fun buffs(sp: SimParticipant): List<Buff> = listOf(buff)
}
