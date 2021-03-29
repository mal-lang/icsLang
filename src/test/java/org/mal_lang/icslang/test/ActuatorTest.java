package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class ActuatorTest extends IcsLangTest {
    private static class ActuatorTestModel {
        public final Actuator actuator = new Actuator("actuator");
        public final IcsSystem icsSystem = new IcsSystem("icsSystem");

        public ActuatorTestModel() {
            actuator.addSystem(icsSystem);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testActuatorManipulate() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new ActuatorTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.actuator.manipulate);
        attacker.attack();

        model.icsSystem.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testActuatorBlock() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new ActuatorTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.actuator.block);
        attacker.attack();

        model.icsSystem.lossOfControl.assertCompromisedInstantaneously();
    }
}
