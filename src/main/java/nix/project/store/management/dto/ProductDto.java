package nix.project.store.management.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nix.project.store.management.models.enums.Units;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Double price;
    private Units units;
    private String type;

    @Override
    @JsonValue
    public String toString(){

        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonNodes = jsonMapper.createObjectNode();

        jsonNodes.put("id", id);
        jsonNodes.put("name", name);
        jsonNodes.put("price", price);
        jsonNodes.put("units", units.toString());
        jsonNodes.put("type", type);

        try {
            return  jsonMapper
                    .writeValueAsString(jsonNodes);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        return this.toString();
    }
}
