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

public class DuckActionsTestProperties extends DuckClient {

    @Test(description = "Показ характеристик резиновой уточки")
    @CitrusTest
    public void successfulPropertiesRubber(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do{
            Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
            createDuck(runner, duck);
            id = getId(runner);
            even = checkId(id);
        }while (even == 1);
        Duck duck = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        duckProperties(runner, id);
        validateResponseProperties(runner,duck);
    }

    @Test(description = "Показ характеристик деревянной уточки, но характеристики не отображаются")
    @CitrusTest
    public void unsuccessfulPropertiesWood(@Optional @CitrusResource TestCaseRunner runner) {
        int even;
        String id;
        do {
            Duck duck = new Duck().color("yellow").height(0.01).material("wood").sound("quack").wingsState(WingState.ACTIVE);
            createDuck(runner, duck);
            id = getId(runner);
            even = checkId(id);
        } while (even == 0);
        Duck duck = new Duck().color("yellow").height(1.0).material("rubber").sound("quack").wingsState(WingState.ACTIVE);
        duckProperties(runner, id);
        validateResponseProperties(runner,duck);
    }
}
