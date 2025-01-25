package autotests.tests;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckTestDelete extends DuckClient {

    @Test(description = "Удаление утки")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        String id = getId(runner);
        duckDelete(runner, id);
        validateResponse(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }
}