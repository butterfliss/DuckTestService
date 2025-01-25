package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

    public String getId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId")));
        return "${duckId}";
    }

    public int checkId (String id){
        try {
            int intId = Integer.parseInt(id);
            if ((intId % 2) == 0){
                return 0;
            } else{ return 1;}

        } catch (NumberFormatException e) {
            System.err.println("Неправильный формат строки!");
        }
        return 2;
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
    }

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

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id));
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id));
    }

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

    public void validateResponseCreate(TestCaseRunner runner, String material) {
        runner.$(
                http()
                        .client(yellowDuckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
                        .body("{\n" + "  \"id\": " + "${duckId}" + ","
                                + "  \"color\": \"" + "yellow" + "\","
                                + "  \"height\": " + 1.0 + ","
                                + "  \"material\": \"" + material + "\","
                                + "  \"sound\": \"" + "quack" + "\","
                                + "  \"wingsState\": \"" + "ACTIVE"
                                + "\"\n" + "}"));
    }

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
}
