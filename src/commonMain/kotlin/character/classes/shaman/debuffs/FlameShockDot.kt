package character.classes.shaman.debuffs

import character.Ability
import character.Debuff
import character.Proc
import data.Constants
import mechanics.Spell
import sim.Event
import sim.EventResult
import sim.EventType

import sim.SimParticipant

class FlameShockDot(owner: SimParticipant) : Debuff(owner) {
    override val name: String = "Flame Shock (DoT)"
    override val durationMs: Int = 12000
    override val tickDeltaMs: Int = 3000

    val fsdAbility = object : Ability() {
        override val id: Int = 25457
        override val name: String = "Flame Shock (DoT)"
        override fun gcdMs(sp: SimParticipant): Int = 0

        val dmgPerTick = 105.0
        val numTicks = 4.0
        val school = Constants.DamageType.FIRE
        override fun cast(sp: SimParticipant) {
            val spellPowerCoeff = Spell.spellPowerCoeff(0, durationMs) / numTicks
            val damageRoll = Spell.baseDamageRollSingle(owner, dmgPerTick, school, spellPowerCoeff)

            val event = Event(
                eventType = EventType.DAMAGE,
                damageType = school,
                ability = this,
                amount = damageRoll,
                result = EventResult.HIT
            )
            owner.logEvent(event)

            owner.fireProc(listOf(Proc.Trigger.FIRE_DAMAGE_PERIODIC), listOf(), this, event)
        }
    }

    override fun tick(sp: SimParticipant) {
        fsdAbility.cast(sp)
    }
}
