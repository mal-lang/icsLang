package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class ControlSignalTest extends IcsLangTest {
    private static class ControlSignalTestModel {
        public final ControlSignal controlSignal = new ControlSignal("controlSignal");
        public final Actuator actuator = new Actuator("actuator");
        public final IcsApplication destApp = new IcsApplication("destApp");

        public ControlSignalTestModel() {
            controlSignal.addControlSignalDestApp(destApp);
            controlSignal.addSignalActuator(actuator);
        }
        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
  }


    @Test
    public void testManipulateControlSignal() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new ControlSignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.controlSignal.manipulateSignal);
        attacker.attack();

        model.actuator.manipulate.assertCompromisedInstantaneously();
        model.destApp.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testBlockControlSignal() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new ControlSignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.controlSignal.blockSignal);
        attacker.attack();

        model.actuator.block.assertCompromisedInstantaneously();
        model.destApp.lossOfControl.assertCompromisedInstantaneously();
    }


}
