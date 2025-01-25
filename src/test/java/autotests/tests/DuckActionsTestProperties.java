package autotests.tests;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckActionsTestProperties extends DuckClient {

    @Test(description = "Показ характеристик резиновой уточки")
    @CitrusTest
    public void successfulPropertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do{
            createDuck(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
            id = getId(runner);
            even = checkId(id);
        }while (even == 1);
        duckProperties(runner, id);
        validateResponseProperties(runner,"yellow", 100, "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Показ характеристик деревянной уточки, но характеристики не отображаются")
    @CitrusTest
    public void unsuccessfulPropertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do {
            createDuck(runner, "yellow", 1, "wood", "quack", "ACTIVE");
            id = getId(runner);
            even = checkId(id);
        } while (even == 0);
        duckProperties(runner, id);
        validateResponseProperties(runner, "yellow", 100, "wood", "quack", "ACTIVE");
    }
}
