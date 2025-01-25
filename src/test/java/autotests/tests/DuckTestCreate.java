package autotests.tests;

import autotests.clients.DuckClient;
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
        createDuck(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        validateResponseCreate(runner, "rubber");
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulCreationWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        validateResponseCreate(runner, "wood");
    }
}
