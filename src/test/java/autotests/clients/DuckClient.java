package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends BaseTest {

    @Autowired
    protected HttpClient yellowDuckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Эндпоинт для получения id")
    public void getId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId")));
    }

    public void deleteFromDB(TestCaseRunner runner) {
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
    }

    @Step("Эндпоинт для получения характеристик уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner,
                yellowDuckService,
                "/api/duck/action/properties?id=${duckId}");
    }

    @Step("Эндпоинт для кряканья уточки")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        sendGetRequest(runner,
                yellowDuckService,
                "/api/duck/action/quack?id=${duckId}&repetitionCount="+ repetitionCount +"&soundCount="+ soundCount);
    }

    @Step("Эндпоинт для полёта уточки")
    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequest(runner,
                yellowDuckService,
                "/api/duck/action/fly?id=${duckId}");
    }

    @Step("Эндпоинт для плавания уточки")
    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequest(runner,
                yellowDuckService,
                "/api/duck/action/swim?id=${duckId}");
    }

    @Step("Эндпоинт для удаления уточки")
    public void duckDelete(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner,
                yellowDuckService,
                "/api/duck/delete?id=${duckId}");
    }

    @Step("Эндпоинт для обновления характеристик уточки")
    public void duckUpdate(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        sendUpdateRequest(runner,
                yellowDuckService,
                "/api/duck/update?color="+ color +"&height="+ height +"&id=${duckId}&material="
                        + material +"&sound="+ sound +"&wingsState="+ wingsState);
    }

    @Step("Эндпоинт для валидации характеристик")
    public void validateResponseProperties(TestCaseRunner runner, Object body) {
        receiveResponsePropertiesRequest(runner,
                yellowDuckService,
                body);
    }

    @Step("Эндпоинт для валидации ответов")
    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        receiveResponseRequest(runner,
                yellowDuckService,
                responseMessage);
    }

    @Step("Эндпоинт для валидации ответов")
    public void validateResponseTestCreation(TestCaseRunner runner, String responseMessage) {
        receiveValidateResponseRequest(runner,
                yellowDuckService,
                responseMessage);
    }

    @Step("Эндпоинт для валидации обновления")
    public void validateResponseUpdate(TestCaseRunner runner, String responseMessage) {
        receiveResponseUpdateRequest(runner,
                yellowDuckService,
                responseMessage);
    }

    @Step("Эндпоинт для валидации создания уточки")
    public void validateResponseCreate(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        receiveResponseCreateRequest(runner,
                yellowDuckService,
                color,
                height,
                material,
                sound,
                wingsState);
    }

    @Step("Эндпоинт для создания уточки")
    public void createDuck(TestCaseRunner runner, Object body) {
        sendCreateRequest(runner,
                yellowDuckService,
                "/api/duck/create",
                body);
    }

    @Step("Эндпоинт для обновления бд")
    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    @Step("Эндпоинт для обновления бд")
    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                          String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }

    @Step("Эндпоинт для получения всех id")
    public void getAllIds(TestCaseRunner runner) {
        sendGetRequest(runner,
                yellowDuckService,
                "/api/duck/getAllIds");
    }
}
