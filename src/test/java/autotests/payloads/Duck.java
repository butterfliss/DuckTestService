package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class Duck {
   // @JsonInclude(JsonInclude.Include.NON_NULL)
   // @JsonProperty("id")
   // private int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("color")
    private String color;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("height")
    private Double height;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("material")
    private String material;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sound")
    private String sound;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("wingsState")
    private WingState wingsState;
}