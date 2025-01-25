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

public class DuckActionsTestSwim extends DuckClient {

    @Test(description = "Попытка утки поплыть с существующим id")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "DuckActionTest/successfulSwim.json");
    }

    @Test(description = "Попытка утки поплыть с несуществующим id")
    @CitrusTest
    public void unsuccessfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckSwim(runner,  "9999");
        validateResponse(runner, "DuckActionTest/unsuccessfulSwim.json");
    }
}
