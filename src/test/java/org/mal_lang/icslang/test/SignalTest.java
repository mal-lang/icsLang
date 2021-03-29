package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class SignalTest extends IcsLangTest {
    private static class SignalTestModel {
        public final Signal signal = new Signal("signal");
        public final Data data = new Data("data");
        public final Data encSignal = new Data("encSignal");
        public final Signal nonExistentSignal = new Signal("nonExistentSignal", false, true);
        public final Credentials signalCreds = new Credentials("signalCreds");
        public final IcsApplication destApp = new IcsApplication("destApp");

        public SignalTestModel() {
            signal.addContainedData(data);
            encSignal.addEncryptCreds(signalCreds);
            signal.addSignalDestApp(destApp);
        }
        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
  }

    @Test
    public void testDataInSignal() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.signal.attemptAccess);
        attacker.attack();

        model.signal.access.assertCompromisedInstantaneously();
        model.signal.readContainedInformationAndData.assertCompromisedInstantaneously();
        model.data.access.assertCompromisedInstantaneously();
    }

    @Test
    public void testSignalAndDataNoAccess() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SignalTestModel();

        var attacker = new Attacker();
        attacker.attack();

        model.signal.access.assertUncompromised();
        model.signal.read.assertUncompromised();
        model.signal.write.assertUncompromised();
        model.signal.delete.assertUncompromised();

        model.data.access.assertUncompromised();
        model.data.read.assertUncompromised();
        model.data.write.assertUncompromised();
        model.data.delete.assertUncompromised();

        model.encSignal.access.assertUncompromised();
        model.encSignal.read.assertUncompromised();
        model.encSignal.write.assertUncompromised();
        model.encSignal.delete.assertUncompromised();

        model.nonExistentSignal.access.assertUncompromised();
        model.nonExistentSignal.read.assertUncompromised();
        model.nonExistentSignal.write.assertUncompromised();
        model.nonExistentSignal.delete.assertUncompromised();
    }

    @Test
    public void testDecryptSignalUncompromisedCredentials() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.encSignal.attemptAccess);
        attacker.attack();

        model.encSignal.access.assertUncompromised();
        model.encSignal.read.assertUncompromised();
        model.encSignal.write.assertUncompromised();
        model.encSignal.delete.assertUncompromised();
    }

    @Test
    public void testManipulateSignal() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.signal.manipulateSignal);
        attacker.attack();

        model.destApp.manipulationOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testBlockSignal() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SignalTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.signal.blockSignal);
        attacker.attack();

        model.destApp.lossOfView.assertCompromisedInstantaneously();
    }


}
