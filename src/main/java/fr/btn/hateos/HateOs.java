package fr.btn.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class HateOs {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Link> links;
    public void addLink(Link link) {
        if(links == null)
            links = new ArrayList<>();
        links.add(link);
    }

    public void addLink(String name, String method, URI uri) {
        if(links == null)
            links = new ArrayList<>();
        links.add(new Link(name, method, uri));
    }

    public void addLink(String name, String method, URI uri, Object parameter) {
        if(links == null)
            links = new ArrayList<>();
        links.add(new Link(name, method, uri, parameter));
    }

}
