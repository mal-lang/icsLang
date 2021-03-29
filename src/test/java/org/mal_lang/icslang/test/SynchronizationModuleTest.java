package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class SynchronizationModuleTest extends IcsLangTest {
    private static class SynchronizationModuleTestModel {
        public final SynchronizationModule synchronizationModule = new
            SynchronizationModule("synchronizationModule");
        public final IcsApplication icsApplication = new
            IcsApplication("icsApplication");

        public SynchronizationModuleTestModel() {
            synchronizationModule.addSynchronizedApp(icsApplication);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testSynchronizationModuleCompromise() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SynchronizationModuleTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.synchronizationModule.compromise);
        attacker.attack();

        model.synchronizationModule.manipulateClockFrequency.assertCompromisedInstantaneously();
        model.synchronizationModule.manipulateTime.assertCompromisedInstantaneously();
        model.synchronizationModule.stopClock.assertCompromisedInstantaneously();
    }

    @Test
    public void testSynchronizationModuleManipulateClockFrequency() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SynchronizationModuleTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.synchronizationModule.manipulateClockFrequency);
        attacker.attack();

        model.icsApplication.manipulationOfControl.assertCompromisedInstantaneously();
        model.icsApplication.manipulationOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testSynchronizationModuleManipulateTime() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SynchronizationModuleTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.synchronizationModule.manipulateTime);
        attacker.attack();

        model.icsApplication.manipulationOfControl.assertCompromisedInstantaneously();
        model.icsApplication.manipulationOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testSynchronizationModuleStopClock() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SynchronizationModuleTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.synchronizationModule.stopClock);
        attacker.attack();

        model.icsApplication.lossOfControl.assertCompromisedInstantaneously();
        model.icsApplication.lossOfView.assertCompromisedInstantaneously();
    }
}
