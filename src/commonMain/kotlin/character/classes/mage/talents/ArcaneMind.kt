package character.classes.mage.talents

import character.Buff
import character.Stats
import character.Talent
import sim.SimParticipant

class ArcaneMind(currentRank: Int) : Talent(currentRank) {
    companion object {
        const val name = "Arcane Mind"
    }
    override val name: String = Companion.name
    override val maxRank: Int = 5

    val buff = object : Buff() {
        override val name: String = Companion.name
        override val durationMs: Int = -1
        override val hidden: Boolean = true
        override val icon: String = "spell_shadow_charm.jpg"

        override fun modifyStats(sp: SimParticipant): Stats {
            return Stats(intellectMultiplier = 1.0 + (currentRank * 0.03))
        }
    }

    override fun buffs(sp: SimParticipant): List<Buff> = listOf(buff)
}
