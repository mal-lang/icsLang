package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class ControllerTest extends IcsLangTest {
    private static class ControllerTestModel {
        public final Controller controller = new
            Controller("controller", false, false, false, true);

        public ControllerTestModel() {
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testControllerAttemptManipulateWithPhysicalLock() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new ControllerTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.controller.attemptManipulation);
        attacker.attack();

        model.controller.manipulate.assertUncompromised();
    }
}
