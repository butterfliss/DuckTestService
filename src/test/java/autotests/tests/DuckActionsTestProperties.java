package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
import autotests.payloads.DuckProperties;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/properties")
public class DuckActionsTestProperties extends DuckClient {

    @Test(description = "Показ характеристик резиновой уточки")
    @CitrusTest
    public void successfulPropertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','ACTIVE');");
        DuckProperties duck = new DuckProperties().color("yellow").height(100.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        duckProperties(runner, "${duckId}");
        validateResponseProperties(runner,duck);
    }

    @Test(description = "Показ характеристик деревянной уточки, но характеристики не отображаются")
    @CitrusTest
    public void unsuccessfulPropertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'wood', 'quack','ACTIVE');");
        DuckProperties duck = new DuckProperties().color("yellow").height(100.0).material("wood").sound("quack").wingsState(WingState.ACTIVE);
        duckProperties(runner, "${duckId}");
        validateResponseProperties(runner,duck);
    }
}