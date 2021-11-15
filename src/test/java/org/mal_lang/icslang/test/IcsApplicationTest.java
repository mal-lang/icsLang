package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class IcsApplicationTest extends IcsLangTest {
    private static class IcsApplicationTestModel {
        public final IcsApplication icsApplication = new
            IcsApplication("icsApplication");

        public IcsApplicationTestModel() {
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    private static class IcsApplicationAndSystemTestModel {
        public final IcsApplication icsApplication = new
            IcsApplication("icsApplication");
        public final IcsSystem icsSystem = new
            IcsSystem("icsSystem");

        public IcsApplicationAndSystemTestModel() {
            icsSystem.addSysExecutedApps(icsApplication);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    private static class IcsApplicationComplexTestModel {
        public final IcsApplication icsApplication = new
            IcsApplication("icsApplication");
        public final IcsSystem icsSystem = new
            IcsSystem("icsSystem");
        public final Signal signal = new Signal("signal");
        public final ControlSignal controlSignal = new
            ControlSignal("controlSignal");
        public final IcsData icsData = new
            IcsData("icsData");
        public final IcsControlData icsControlData = new
            IcsControlData("icsControlData");

        public IcsApplicationComplexTestModel() {
            icsSystem.addSysExecutedApps(icsApplication);
            icsApplication.addTransmittedSignal(signal);
            icsApplication.addTransmittedControlSignal(controlSignal);
            icsApplication.addSentData(icsData);
            icsApplication.addSentData(icsControlData);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testIcsApplicationSpecificAccess() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.specificAccess);
        attacker.attack();

        model.icsApplication.normalOperation.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationFullAccess() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.fullAccess);
        attacker.attack();

        model.icsApplication.normalOperation.assertCompromisedInstantaneously();
        model.icsApplication.attemptManipulation.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationRead() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.read);
        attacker.attack();

        model.icsApplication.theftOfOperationalInformation.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationDeny() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationAndSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.deny);
        attacker.attack();

        model.icsApplication.lossOfControl.assertCompromisedInstantaneously();
        model.icsApplication.lossOfView.assertCompromisedInstantaneously();
        model.icsSystem.lossOfAvailability.assertCompromisedInstantaneously();
        model.icsApplication.manipulationOfControl.assertUncompromised();
        model.icsApplication.manipulationOfView.assertUncompromised();
    }

    @Test
    public void testIcsApplicationManipulate() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.manipulate);
        attacker.attack();

        model.icsApplication.manipulationOfControl.assertCompromisedInstantaneously();
        model.icsApplication.manipulationOfView.assertCompromisedInstantaneously();
        model.icsApplication.lossOfControl.assertUncompromised();
        model.icsApplication.lossOfView.assertUncompromised();
    }

    @Test
    public void testIcsApplicationManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationComplexTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.manipulationOfControl);
        attacker.attack();

        model.icsApplication.restrictedOperation.assertCompromisedInstantaneously();
        model.controlSignal.manipulateSignal.assertCompromisedInstantaneously();
        model.icsControlData.attemptWrite.assertCompromisedInstantaneously();
        model.icsSystem.manipulationOfControl.assertCompromisedInstantaneously();
        model.icsSystem.unsafeState.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationLossOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationComplexTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.lossOfControl);
        attacker.attack();

        model.icsApplication.restrictedOperation.assertCompromisedInstantaneously();
        model.controlSignal.blockSignal.assertCompromisedInstantaneously();
        model.icsControlData.attemptDeny.assertCompromisedInstantaneously();
        model.icsSystem.lossOfControl.assertCompromisedInstantaneously();
        model.icsSystem.attemptPreemptiveShutdown.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationComplexTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.manipulationOfView);
        attacker.attack();

        model.icsApplication.restrictedOperation.assertCompromisedInstantaneously();
        model.signal.manipulateSignal.assertCompromisedInstantaneously();
        model.icsData.attemptWrite.assertCompromisedInstantaneously();
        model.icsSystem.manipulationOfView.assertCompromisedInstantaneously();
        model.icsSystem.unsafeState.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsApplicationLossOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsApplicationComplexTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsApplication.lossOfView);
        attacker.attack();

        model.icsApplication.restrictedOperation.assertCompromisedInstantaneously();
        model.signal.blockSignal.assertCompromisedInstantaneously();
        model.icsData.attemptDeny.assertCompromisedInstantaneously();
        model.icsSystem.lossOfView.assertCompromisedInstantaneously();
        model.icsSystem.attemptPreemptiveShutdown.assertCompromisedInstantaneously();
    }
}
