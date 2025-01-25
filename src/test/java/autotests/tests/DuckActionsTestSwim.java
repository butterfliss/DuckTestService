package autotests.tests;

import autotests.clients.DuckClient;
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
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        String id = getId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I’m swimming\"\n" + "}");
    }

    @Test(description = "Попытка утки поплыть с несуществующим id")
    @CitrusTest
    public void unsuccessfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        String id = getId(runner);
        duckSwim(runner,  "9999");
        validateResponse(runner, "{\n" + "  \"message\": \"ID is not found\"\n" + "}");
    }
}
