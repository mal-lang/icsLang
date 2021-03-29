package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class SensorTest extends IcsLangTest {
    private static class SensorTestModel {
        public final Sensor sensor = new Sensor("sensor");
        public final Signal signal = new Signal("signal");
        public final IcsData icsData = new IcsData("icsData");
        public final IcsSystem icsSystem = new IcsSystem("icsSystem");

        public SensorTestModel() {
            sensor.addSignal(signal);
            sensor.addData(icsData);
            sensor.addSystem(icsSystem);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testSensorPhysicalAccess() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new SensorTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.sensor.physicalAccess);
        attacker.attack();

        model.signal.manipulateSignal.assertCompromisedInstantaneously();
        model.signal.blockSignal.assertCompromisedInstantaneously();
        model.icsData.attemptWrite.assertCompromisedInstantaneously();
        model.icsData.attemptDeny.assertCompromisedInstantaneously();
        model.icsSystem.lossOfView.assertCompromisedInstantaneously();
        model.icsSystem.manipulationOfView.assertCompromisedInstantaneously();
    }
}
