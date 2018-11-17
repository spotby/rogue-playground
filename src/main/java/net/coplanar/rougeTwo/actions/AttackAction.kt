package net.coplanar.rougeTwo.actions

import com.valkryst.VDice.DiceRoller
import net.coplanar.rougeTwo.Entity
import net.coplanar.rougeTwo.Message
import net.coplanar.rougeTwo.controllers.GameController
import net.coplanar.rougeTwo.BoundStat



class AttackAction(target:Entity) : Action() {

    val target: Entity
    val actionRoll: Int

    init {
        this.target = target
        val diceRoller =  DiceRoller()
        diceRoller.addDice(20,1)
        actionRoll = diceRoller.roll()

    }

    override fun perform(gameControl: GameController, actor: Entity) {
        var damage = 0
        var message = Message()

        // Critical Miss
        if (actionRoll == 1)  {
            damage = calculateDamage(actor, actor);
            var health = actor.getStat("health") as BoundStat
            health.value = health.value - damage
            message = getCriticalMissMessage(actor,damage)
            if (health.value <= health.minValue) {
                DeathAction().perform(gameControl, actor)
            }
        }
        if (actionRoll > 1 && actionRoll < 5) {
            message = getAttackMissMessage(actor);
        }
        if (actionRoll >=5 && actionRoll <= 16) {
            damage = calculateDamage(actor, target);
            message = getHitMessage(actor, damage);
        }
        if (actionRoll > 16 && actionRoll < 20) {
            damage = calculateDamage(actor, target) * 2;
            message = getDoubleHitMessage(actor, damage);
        }
        if(actionRoll == 20) {
            damage = calculateDamage(actor, target) * 3;
            message = getCriticalHitMessage(actor, damage);
        }

        var targetHealth = target.getStat("Health") as BoundStat
        if (damage > 0) {
            targetHealth.value = targetHealth.value - damage
            gameControl.displayMessage(message)
            println(" ${target.name} has ${targetHealth.value} HP left")
        } else {
            gameControl.displayMessage(getDodgeMessage(actor));
        }
        super.perform(gameControl, actor)
        if (targetHealth.value <= targetHealth.minValue) {
            DeathAction().perform(gameControl, target)
        }

    }

    fun calculateDamage(actor: Entity, target: Entity): Int {
        val armor  = target.getArmor()
        val rawDamage = actor.getDamage()
        val result = rawDamage - armor
        return if (result > 0) result else 0
    }

    fun getCriticalMissMessage(actor:Entity, damage:Int):Message {
        return Message()
                .appendEntityName(actor)
                .append(" missed and attacked itself for ${damage} damage.");
    }

    fun getAttackMissMessage(actor:Entity):Message {
        return Message()
                .appendEntityName(actor)
                .append(" missed its target");
    }

    fun getHitMessage(actor:Entity, damage:Int):Message {
        return Message()
                .appendEntityName(actor)
                .append(" hit ")
                .appendEntityName(target)
                .append(" for ${damage} damage.");
    }

    fun getDoubleHitMessage(actor:Entity, damage:Int):Message {
        return Message()
                .appendEntityName(actor)
                .append(" landed a heavy attack on ")
                .appendEntityName(target)
                .append(" for ${damage} damage.");
    }

    fun getCriticalHitMessage(actor:Entity, damage:Int):Message {
        return Message()
                .appendEntityName(actor)
                .append(" landed a CRITICAL HIT on ")
                .appendEntityName(target)
                .append(" for ${damage} damage.");
    }

    fun getDodgeMessage(actor:Entity):Message {
        return Message()
                .appendEntityName(target)
                .append(" dodged ")
                .appendEntityName(actor)
                .append("'s attack!")
    }



}