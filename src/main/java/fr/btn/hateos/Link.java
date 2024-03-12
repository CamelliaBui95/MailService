package fr.btn.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
    private String name;
    private String method;
    private URI uri;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object parameter;

    public Link(String name, String method, URI uri) {
        this.name = name;
        this.method = method;
        this.uri = uri;
    }

}
