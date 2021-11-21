package character.classes.rogue.debuffs

import character.Ability
import character.Debuff
import data.Constants
import sim.Event
import sim.SimParticipant
import character.Proc
import character.classes.rogue.talents.*
import sim.EventResult
import sim.EventType

class GarroteDot(owner: SimParticipant) : Debuff(owner) {
    companion object {
        const val name = "Garrote (DoT)"
    }

    override val name: String = Companion.name
    override val durationMs: Int = 18000
    override val tickDeltaMs: Int = 3000

    val dot = object : Ability() {
        override val id: Int = 26884
        override val name: String = Companion.name
        override fun gcdMs(sp: SimParticipant): Int = 0

        fun dmgPerTick(sp: SimParticipant): Double{
            val opportunity = sp.character.klass.talents[Opportunity.name] as Opportunity?
            val increasedDamagePercent = opportunity?.damageIncreasePercent() ?: 0.0

            val dmgMultiplier = 1 + (increasedDamagePercent / 100.0).coerceAtLeast(0.0)

            return (810.0 * dmgMultiplier + sp.attackPower() * 0.18) / 6
        }

        override fun cast(sp: SimParticipant) {
            val event = Event(
                eventType = EventType.DAMAGE,
                damageType = Constants.DamageType.PHYSICAL,
                ability = this,
                amount = dmgPerTick(sp),
                result = EventResult.HIT
            )
            owner.logEvent(event)

            owner.fireProc(listOf(Proc.Trigger.PHYSICAL_DAMAGE_PERIODIC), listOf(), this, event)
        }
    }

    override fun tick(sp: SimParticipant) {
        dot.cast(owner)
    }
}
