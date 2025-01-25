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

public class DuckActionsTestFly extends DuckClient {

    @Test(description = "Полёт уточки с активными крыльями")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        createDuck(runner, duck);
        String id = getId(runner);
        duckFly(runner, id);
        validateResponse(runner, "DuckActionTest/successfulFly.json");
    }

    @Test(description = "Полёт уточки с неактивными крыльями")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckFly(runner, id);
        validateResponse(runner, "DuckActionTest/unsuccessfulFly.json");
    }

    @Test(description = "Полёт уточки с неопределёнными крыльями")
    @CitrusTest
    public void unsuccessfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.UNDEFINED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckFly(runner, id);
        validateResponse(runner, "DuckActionTest/unsuccessfulFly.json");
    }
}
