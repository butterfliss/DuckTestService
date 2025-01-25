package autotests.tests;

import autotests.clients.DuckClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckTestCreate extends DuckClient{

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulCreationRubber(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        validateResponseCreate(runner, "rubber");
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulCreationWood(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(1.0).material("wood").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        validateResponseCreate(runner, "wood");
    }
}
