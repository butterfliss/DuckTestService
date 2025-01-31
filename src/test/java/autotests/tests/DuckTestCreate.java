package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class DuckTestCreate extends DuckClient {
    Duck duckProperties1 = new Duck()
            .color("yellow")
            .height(0.05)
            .material("rubber")
            .sound("quack")
            .wingsState(WingState.ACTIVE);
    Duck duckProperties2 = new Duck()
            .color("black")
            .height(0.05)
            .material("plastic")
            .sound("quack")
            .wingsState(WingState.ACTIVE);
    Duck duckProperties3 = new Duck()
            .color("blue")
            .height(3.05)
            .material("rubber")
            .sound("quack")
            .wingsState(WingState.FIXED);
    Duck duckProperties4 = new Duck()
            .color("purple")
            .height(5.0)
            .material("rubber")
            .sound("quack")
            .wingsState(WingState.ACTIVE);
    Duck duckProperties5 = new Duck()
            .color("green")
            .height(1.0)
            .material("glass")
            .sound("moo")
            .wingsState(WingState.ACTIVE);

    @Test(dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload", "response", "runner"})
    public void successfulCreation(Object payload, String response, @Optional @CitrusResource TestCaseRunner runner)  {
        deleteFromDB(runner);
        createDuck(runner, payload);
        validateResponseTestCreation(runner, response);
    }

    @DataProvider(name = "duckList")
    public Object[][] DuckProvider() {
        return new Object[][]{
                {duckProperties1, "getDuckPropertiesTest/duckYellowProperties.json", null},
                {duckProperties2, "getDuckPropertiesTest/duckBlackProperties.json", null},
                {duckProperties3, "getDuckPropertiesTest/duckBlueProperties.json", null},
                {duckProperties4, "getDuckPropertiesTest/duckPurpleProperties.json", null},
                {duckProperties5, "getDuckPropertiesTest/duckGreenProperties.json", null}
        };

    }

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulCreationRubber(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        validateResponseCreate(runner, "yellow", 1.0, "rubber", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "yellow", "1.0", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulCreationWood(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("wood").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        validateResponseCreate(runner, "yellow", 1.0, "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "yellow", "1.0", "wood", "quack", "ACTIVE");
    }

}
