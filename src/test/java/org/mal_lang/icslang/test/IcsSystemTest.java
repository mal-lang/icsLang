package org.mal_lang.icslang.test;

import core.Attacker;
import core.AttackStep;
import org.junit.jupiter.api.Test;

public class IcsSystemTest extends IcsLangTest {
    private static class IcsSystemSubsystemsTestModel {
        public final IcsSystem parentIcsSystem = new
            IcsSystem("parentIcsSystem",
                    false, false, false, false, false, true);
        public final IcsSystem criticalIcsSubsystem = new
            IcsSystem("criticalIcsSubsystem");
        public final IcsSystem redundantIcsSubsystem1 = new
            IcsSystem("redundantIcsSubsystem1");
        public final IcsSystem redundantIcsSubsystem2 = new
            IcsSystem("redundantIcsSubsystem2");

        public IcsSystemSubsystemsTestModel() {
            parentIcsSystem.addCriticalSubsystems(criticalIcsSubsystem);
            parentIcsSystem.addRedundantSubsystems(redundantIcsSubsystem1);
            parentIcsSystem.addRedundantSubsystems(redundantIcsSubsystem2);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    private static class IcsSystemDisabledRedundantSubsystemTestModel {
        public final IcsSystem parentIcsSystem = new
            IcsSystem("parentIcsSystem");
        public final IcsSystem redundantIcsSubsystem = new
            IcsSystem("redundantIcsSubsystem");
        public final IcsSystem disabledRedundantIcsSubsystem = new
            IcsSystem("disabledRedundantIcsSubsystem",
                    false, false, false, false, false, false);

        public IcsSystemDisabledRedundantSubsystemTestModel() {
            parentIcsSystem.addRedundantSubsystems(redundantIcsSubsystem);
            parentIcsSystem.addRedundantSubsystems(disabledRedundantIcsSubsystem);
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    private static class IcsSystemTestModel {
        public final IcsSystem icsSystem = new IcsSystem("icsSystem");

        public IcsSystemTestModel() {
        }

        public void addAttacker(Attacker attacker, AttackStep attackpoint) {
            attacker.addAttackPoint(attackpoint);
        }
    }

    // Impact Attack Steps Tests

    /* TODO: Add tests for the non-deterministic impact attack steps on Loss
     * of Control and View.
     */

    @Test
    public void testIcsSystemImpactManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.manipulationOfControl);
        attacker.attack();

        /* Verify that no safety mechanisms are operating as the IcsSystem is
         * not connected to an SIS.
         */
        model.icsSystem.safetyMechanismsOffline.assertCompromisedInstantaneously();

        // Verify that all of the impact attack steps trigger
        model.icsSystem.unsafeState.assertCompromisedInstantaneously();
        model.icsSystem.shutdown.assertCompromisedInstantaneously();
        model.icsSystem.lossOfAvailability.assertCompromisedInstantaneously();
        model.icsSystem.lossOfProductivityAndRevenue.assertCompromisedInstantaneously();
        model.icsSystem.damageToProperty.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemImpactManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.manipulationOfView);
        attacker.attack();

        /* Verify that no safety mechanisms are operating as the IcsSystem is
         * not connected to an SIS.
         */
        model.icsSystem.safetyMechanismsOffline.assertCompromisedInstantaneously();

        // Verify that all of the impact attack steps trigger
        model.icsSystem.unsafeState.assertCompromisedInstantaneously();
        model.icsSystem.shutdown.assertCompromisedInstantaneously();
        model.icsSystem.lossOfAvailability.assertCompromisedInstantaneously();
        model.icsSystem.lossOfProductivityAndRevenue.assertCompromisedInstantaneously();
        model.icsSystem.damageToProperty.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemImpactDamageToProperty() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.damageToProperty);
        attacker.attack();

        // Verify that all of the impact attack steps trigger
        model.icsSystem.shutdown.assertCompromisedInstantaneously();
        model.icsSystem.lossOfAvailability.assertCompromisedInstantaneously();
        model.icsSystem.lossOfProductivityAndRevenue.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemImpactShutdown() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.shutdown);
        attacker.attack();

        // Verify that all of the impact attack steps trigger
        model.icsSystem.lossOfAvailability.assertCompromisedInstantaneously();
        model.icsSystem.lossOfProductivityAndRevenue.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemImpactLossOfAvailability() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.icsSystem.lossOfAvailability);
        attacker.attack();

        // Verify that all of the impact attack steps trigger
        model.icsSystem.lossOfProductivityAndRevenue.assertCompromisedInstantaneously();
    }

    // Critical Subsystems Tests

    @Test
    public void testIcsSystemCriticalSubsystemLossOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.criticalIcsSubsystem.lossOfControl);
        attacker.attack();

        model.parentIcsSystem.lossOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemCriticalSubsystemLossOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.criticalIcsSubsystem.lossOfView);
        attacker.attack();

        model.parentIcsSystem.lossOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemCriticalSubsystemLossOfAvailability() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.criticalIcsSubsystem.lossOfAvailability);
        attacker.attack();

        model.parentIcsSystem.lossOfAvailability.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemCriticalSubsystemManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.criticalIcsSubsystem.manipulationOfControl);
        attacker.attack();

        model.parentIcsSystem.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemCriticalSubsystemManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.criticalIcsSubsystem.manipulationOfView);
        attacker.attack();

        model.parentIcsSystem.manipulationOfView.assertCompromisedInstantaneously();
    }

    // Redundant Subsystems Test

    @Test
    public void testIcsSystemRedundantSubsystemSingleChildCompromisedLossOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfControl);
        attacker.attack();

        model.parentIcsSystem.lossOfControl.assertUncompromised();
    }

    @Test
    public void testIcsSystemRedundantSubsystemSingleChildCompromisedLossOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfView);
        attacker.attack();

        model.parentIcsSystem.lossOfView.assertUncompromised();
    }

    @Test
    public void testIcsSystemRedundantSubsystemSingleChildCompromisedLossOfAvailability() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfAvailability);
        attacker.attack();

        model.parentIcsSystem.lossOfAvailability.assertUncompromised();
        model.redundantIcsSubsystem2.lossOfAvailability.assertUncompromised();
    }

    @Test
    public void testIcsSystemRedundantSubsystemSingleChildCompromisedManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.manipulationOfControl);
        attacker.attack();

        model.parentIcsSystem.manipulationOfControl.assertUncompromised();
    }

    @Test
    public void testIcsSystemRedundantSubsystemSingleChildCompromisedManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.manipulationOfView);
        attacker.attack();

        model.parentIcsSystem.manipulationOfView.assertUncompromised();
    }

    @Test
    public void testIcsSystemRedundantSubsystemsBothChildrenCompromisedLossOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfControl);
        model.addAttacker(attacker,model.redundantIcsSubsystem2.lossOfControl);
        attacker.attack();

        model.parentIcsSystem.lossOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantSubsystemsBothChildrenCompromisedLossOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfView);
        model.addAttacker(attacker,model.redundantIcsSubsystem2.lossOfView);
        attacker.attack();

        model.parentIcsSystem.lossOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantSubsystemsBothChildrenCompromisedLossOfAvailability() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.lossOfAvailability);
        model.addAttacker(attacker,model.redundantIcsSubsystem2.lossOfAvailability);
        attacker.attack();

        model.parentIcsSystem.lossOfAvailability.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantSubsystemsBothChildrenCompromisedManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.manipulationOfControl);
        model.addAttacker(attacker,model.redundantIcsSubsystem2.manipulationOfControl);
        attacker.attack();

        model.parentIcsSystem.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantSubsystemsBothChildrenCompromisedManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemSubsystemsTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem1.manipulationOfView);
        model.addAttacker(attacker,model.redundantIcsSubsystem2.manipulationOfView);
        attacker.attack();

        model.parentIcsSystem.manipulationOfView.assertCompromisedInstantaneously();
    }

    // Redundant Subsystems With One Child Disabled Test

    @Test
    public void testIcsSystemRedundantDisabledSubsystemLossOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemDisabledRedundantSubsystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem.lossOfControl);
        attacker.attack();

        model.parentIcsSystem.lossOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantDisabledSubsystemLossOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemDisabledRedundantSubsystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem.lossOfView);
        attacker.attack();

        model.parentIcsSystem.lossOfView.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantDisabledSubsystemLossOfAvailability() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemDisabledRedundantSubsystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem.lossOfAvailability);
        attacker.attack();

        model.parentIcsSystem.lossOfAvailability.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantDisabledSubsystemManipulationOfControl() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemDisabledRedundantSubsystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem.manipulationOfControl);
        attacker.attack();

        model.parentIcsSystem.manipulationOfControl.assertCompromisedInstantaneously();
    }

    @Test
    public void testIcsSystemRedundantDisabledSubsystemManipulationOfView() {
        printTestName(Thread.currentThread().getStackTrace()[1].getMethodName());
        var model = new IcsSystemDisabledRedundantSubsystemTestModel();

        var attacker = new Attacker();
        model.addAttacker(attacker,model.redundantIcsSubsystem.manipulationOfView);
        attacker.attack();

        model.parentIcsSystem.manipulationOfView.assertCompromisedInstantaneously();
    }

}
