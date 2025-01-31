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

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/update")
public class DuckTestUpdate extends DuckClient {

    @Test(description = "Обновление цвета и высоты утки")
    @CitrusTest
    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 150.0, 'rubber', 'quack','ACTIVE');");
        duckUpdate(runner, "${duckId}", "black", "1", "rubber", "quack", "ACTIVE");
        validateResponseUpdate(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        validateDuckInDatabase(runner, "${duckId}","black", "1.0", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","123");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','ACTIVE');");
        duckUpdate(runner, "${duckId}", "black", "0.15", "rubber", "moo", "ACTIVE");
        validateResponseUpdate(runner, "{\n" + "  \"message\": \"Sound can not be updated\"\n" + "}");
        validateDuckInDatabase(runner, "${duckId}","black", "0.15", "rubber", "moo", "ACTIVE");
    }
}