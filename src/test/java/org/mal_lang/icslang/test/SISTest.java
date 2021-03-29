package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class SISTest extends IcsLangTest {
    private static class SISTestModel {
        public final SIS sis = new SIS("sis");
        public final SIS disabledSis = new SIS("disabledSis",
                false, false, false, false, false, false, false);
        public final SIS redundantSISSubsystem = new
            SIS("redundantSISSubsystem");
        public final SIS redundantSISSubsystemDisabled = new
            SIS("redundantSISSubsystemDisabled",
                false, false, false, false, false, false, false);

        public final IcsSystem icsSystem = new IcsSystem("icsSystem");
        public final IcsSystem icsSystem2 = new IcsSystem("icsSystem2");

        public SISTestModel() {
            sis.addSafeguardedSystem(icsSystem);
            sis.addRedundantSubsystems(redundantSISSubsystem);
            sis.addRedundantSubsystems(redundantSISSubsystemDisabled);
            disabledSis.addSafeguardedSystem(icsSystem2);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testSISPreventDamageToSystem() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SISTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.manipulationOfControl);
        attacker.attack();

        model.icsSystem.damageToProperty.assertUncompromised();
    }

    @Test
    public void testSISShutdown() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SISTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.sis.shutdown);
        attacker.attack();

        model.icsSystem.lossOfSafety.assertCompromisedInstantaneously();
    }

    @Test
    public void testSISDisabled() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SISTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem2.manipulationOfControl);
        attacker.attack();

        model.icsSystem2.damageToProperty.assertCompromisedInstantaneously();
        model.icsSystem2.safetyMechanismsOffline.assertCompromisedInstantaneously();
        model.icsSystem2.lossOfSafety.assertUncompromised();
    }

    @Test
    public void testSISRedundantSubsystemDisabled() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SISTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantSISSubsystem.shutdown);
        attacker.attack();

        model.sis.shutdown.assertCompromisedInstantaneously();
    }
}
