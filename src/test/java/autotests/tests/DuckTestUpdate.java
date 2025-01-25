package autotests.tests;

import autotests.clients.DuckClient;
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
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        String id = getId(runner);
        duckUpdate(runner, id, "black", "1", "rubber", "quack", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        duckProperties(runner, id);
        ShowProperties(runner,"black", 100, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulUpdate2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        String id = getId(runner);
        duckUpdate(runner, id, "black", "0.15", "rubber", "moo", "ACTIVE");
        validateResponse(runner, "{\n" + "  \"message\": \"Sound can not be updated\"\n" + "}");
    }
}