package autotests.tests;

import autotests.clients.DuckClient;
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
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        getId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I’m flying\"\n" + "}");
    }

    @Test(description = "Полёт уточки с неактивными крыльями")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I can’t fly\"\n" + "}");
    }

    @Test(description = "Полёт уточки с неопределёнными крыльями")
    @CitrusTest
    public void unsuccessfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "UNDEFINED");
        String id = getId(runner);
        duckFly(runner, id);
        validateResponse(runner, "{\n" + "  \"message\": \"I can’t fly\"\n" + "}");
    }
}
