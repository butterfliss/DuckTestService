package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
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
@Feature("Эндпоинт /api/duck/action/quack")
public class DuckActionsTestQuack extends DuckClient {
    //Если id нечётный, то утка верно крякает
    //Если id чётный, то утка мычит(
    @Test(description = "Успешный кряк нечётный Id")
    @CitrusTest
    public void successfulQuack(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','ACTIVE');");
        duckQuack(runner, "${duckId}", "1", "1");
        validateResponse(runner, "DuckActionTest/successfulQuack.json");
    }

    @Test(description = "Успешный кряк чётный id")
    @CitrusTest
    public void unsuccessfulQuack(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","124");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','ACTIVE');");
        duckQuack(runner, "${duckId}", "1", "1");
        validateResponse(runner, "DuckActionTest/successfulQuack.json");
    }
}
