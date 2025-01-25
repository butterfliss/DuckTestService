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

public class DuckActionsTestQuack extends DuckClient {
    //Если id нечётный, то утка верно крякает
    //Если id чётный, то утка мычит(
    @Test(description = "Успешный кряк нечётный Id")
    @CitrusTest
    public void successfulQuack(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do{
            Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
            createDuck(runner, duck);
            id = getId(runner);
            even = checkId(id);
        } while (even == 1);
        duckQuack(runner, id, "1", "1");
        validateResponse(runner, "DuckActionTest/successfulQuack.json");
    }

    @Test(description = "Успешный кряк чётный id")
    @CitrusTest
    public void unsuccessfulQuack(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do{
            Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
            createDuck(runner, duck);
            id = getId(runner);
            even = checkId(id);
        } while (even == 0);
        duckQuack(runner, id, "1", "1");
        validateResponse(runner, "DuckActionTest/successfulQuack.json");
    }
}
