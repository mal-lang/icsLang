package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class EngineeringWorkstationTest extends IcsLangTest {
    private static class EngineeringWorkstationTestModel {
        public final EngineeringWorkstation engineeringWorkstation = new
            EngineeringWorkstation("engineeringWorkstation");
        public final Controller controller = new Controller("controller");

        public EngineeringWorkstationTestModel() {
            engineeringWorkstation.addProgrammableControllers(controller);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    @Test
    public void testEngineeringWorkstationFullAccess() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new EngineeringWorkstationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.engineeringWorkstation.fullAccess);
        attacker.attack();

        model.engineeringWorkstation.reprogramControllers.assertCompromisedInstantaneously();
    }

    @Test
    public void testEngineeringWorkstationReprogramControllers() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new EngineeringWorkstationTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.engineeringWorkstation.fullAccess);
        attacker.attack();

        model.controller.attemptManipulation.assertCompromisedInstantaneously();
    }
}
