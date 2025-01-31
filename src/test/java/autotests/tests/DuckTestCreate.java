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
@Feature("Эндпоинт /api/duck/create")
public class DuckTestCreate extends DuckClient{

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulCreationRubber(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        validateResponseCreate(runner, "yellow", 1.0, "rubber", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}","yellow", "1.0", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulCreationWood(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("wood").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        validateResponseCreate(runner, "yellow", 1.0, "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}","yellow", "1.0", "wood", "quack", "ACTIVE");
    }
}
