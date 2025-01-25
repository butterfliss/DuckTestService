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

public class DuckTestUpdate extends DuckClient {

    @Test(description = "Обновление цвета и высоты утки")
    @CitrusTest
    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckUpdate(runner, id, "black", "1", "rubber", "quack", "ACTIVE");
        validateResponseUpdate(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        duckProperties(runner, id);
        ShowProperties(runner,"black", 100, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.15).material("rubber").sound("quack").wingsState(WingState.FIXED);
        createDuck(runner, duck);
        String id = getId(runner);
        duckUpdate(runner, id, "black", "0.15", "rubber", "moo", "ACTIVE");
        validateResponseUpdate(runner, "{\n" + "  \"message\": \"Sound can not be updated\"\n" + "}");
    }
}