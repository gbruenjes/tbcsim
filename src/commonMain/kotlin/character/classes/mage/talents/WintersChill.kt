package character.classes.mage.talents

import character.*
import data.model.Item
import sim.Event
import sim.SimParticipant

class WintersChill(currentRank: Int) : Talent(currentRank) {
    companion object {
        const val name = "Winter's Chill"
    }
    override val name: String = Companion.name
    override val maxRank: Int = 5

    val buff = object : Buff() {
        override val name: String = Companion.name
        override val durationMs: Int = -1
        override val hidden: Boolean = true
        override val icon: String = "spell_frost_chillingblast.jpg"

        val proc = object : Proc() {
            override val triggers: List<Trigger> = listOf(
                Trigger.FROST_DAMAGE
            )
            override val type: Type = Type.PERCENT
            override fun percentChance(sp: SimParticipant): Double = currentRank * 20.0

            fun critDebuff(sp: SimParticipant): Debuff = object : Debuff(sp) {
                override val name: String = Companion.name
                override val durationMs: Int = 15000
                override val maxStacks: Int = 5
            }

            override fun proc(sp: SimParticipant, items: List<Item>?, ability: Ability?, event: Event?) {
                sp.sim.target.addDebuff(critDebuff(sp))
            }
        }

        override fun procs(sp: SimParticipant): List<Proc> = listOf(proc)
    }

    override fun buffs(sp: SimParticipant): List<Buff> = listOf(buff)
}
