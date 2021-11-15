package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class IcsControlDataTest extends IcsLangTest {
    private static class IcsControlDataTestModel {
        public final IcsControlData icsControlData = new IcsControlData("icsControlData");
        public final Actuator actuator = new Actuator("actuator");
        public final IcsApplication destApp = new IcsApplication("destApp");

        public IcsControlDataTestModel() {
            icsControlData.addReceiverApp(destApp);
            icsControlData.addDataActuator(actuator);
        }
        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
  }


    @Test
    public void testIcsControlDataWrite() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsControlDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsControlData.successfulWrite);
        attacker.attack();

        model.actuator.manipulate.assertCompromisedInstantaneously();
        model.destApp.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsControlDataDelete() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsControlDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsControlData.successfulDelete);
        attacker.attack();

        model.actuator.block.assertCompromisedInstantaneously();
        model.destApp.lossOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsControlDataDeny() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsControlDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsControlData.successfulDeny);
        attacker.attack();

        model.actuator.block.assertCompromisedInstantaneously();
        model.destApp.lossOfControl.assertCompromisedInstantaneously();
    }

}
