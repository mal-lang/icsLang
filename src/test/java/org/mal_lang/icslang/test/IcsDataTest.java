package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class IcsDataTest extends IcsLangTest {
    private static class IcsDataTestModel {
        public final IcsData icsData = new IcsData("icsData");
        public final IcsApplication destApp = new IcsApplication("destApp");

        public IcsDataTestModel() {
            icsData.addDataDestApp(destApp);
        }
        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
  }


    @Test
    public void testIcsDataWrite() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsData.successfulWrite);
        attacker.attack();

        model.destApp.manipulationOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsDataDelete() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsData.successfulDelete);
        attacker.attack();

        model.destApp.lossOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsDataDeny() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsDataTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsData.successfulDeny);
        attacker.attack();

        model.destApp.lossOfView.assertCompromisedInstantaneously();
    }

}
