package autotests.tests;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/delete")
public class DuckTestDelete extends DuckClient {

    @Test(description = "Удаление утки")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","126");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 1.0, 'rubber', 'quack','UNDEFINED');");
        duckDelete(runner, "${duckId}");
        validateResponse(runner, "DuckActionTest/successfulDelete.json");
        getAllIds(runner);
        validateResponse(runner, "DuckActionTest/successfulDeleteDB.json");
    }

}