package autotests.tests;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/fly")
public class DuckActionsTestFly extends DuckClient {

    @Test(description = "Полёт уточки с активными крыльями")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','ACTIVE');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, "DuckActionTest/successfulFly.json");
    }

    @Test(description = "Полёт уточки с неактивными крыльями")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','FIXED');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, "DuckActionTest/unsuccessfulFly.json");
    }

    @Test(description = "Полёт уточки с неопределёнными крыльями")
    @CitrusTest
    public void unsuccessfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','UNDEFINED');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, "DuckActionTest/unsuccessfulFly.json");
    }
}
