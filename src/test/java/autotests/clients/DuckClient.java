package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

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

    @Step("Эндпоинт для получения характеристик уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
    }

    @Step("Эндпоинт для кряканья уточки")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repetitionCount)
                        .queryParam("soundCount", soundCount));
    }

    @Step("Эндпоинт для полёта уточки")
    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id));
    }

    @Step("Эндпоинт для плавания уточки")
    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));
    }

    @Step("Эндпоинт для удаления уточки")
    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id));
    }

    @Step("Эндпоинт для обновления характеристик уточки")
    public void duckUpdate(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .put("/api/duck/update")
                        .queryParam("color", color )
                        .queryParam("height", height)
                        .queryParam("id", id)
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState));
    }

    @Step("Эндпоинт для показа характеристик уточки")
    public void ShowProperties(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{" + "  \"color\": \"" + color + "\","
                                + "  \"height\": " + height + ","
                                + "  \"material\": \"" + material + "\","
                                + "  \"sound\": \"" + sound + "\","
                                + "  \"wingsState\": \"" + wingsState
                                + "\"\n" + "}"));
    }

    @Step("Эндпоинт для валидации характеристик")
    public void validateResponseProperties(TestCaseRunner runner, Object body) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    @Step("Эндпоинт для валидации ответов")
    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ClassPathResource(responseMessage)));
    }

    @Step("Эндпоинт для валидации обновления")
    public void validateResponseUpdate(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage));
    }

    @Step("Эндпоинт для валидации создания уточки")
    public void validateResponseCreate(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
                        .body("{\n" + "  \"id\": " + "${duckId}" + ","
                                + "  \"color\": \"" + color + "\","
                                + "  \"height\": " + height + ","
                                + "  \"material\": \"" + material + "\","
                                + "  \"sound\": \"" + sound + "\","
                                + "  \"wingsState\": \"" + wingsState
                                + "\"\n" + "}"));
    }

    @Step("Эндпоинт для создания уточки")
    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
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
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/getAllIds"));
    }
}
