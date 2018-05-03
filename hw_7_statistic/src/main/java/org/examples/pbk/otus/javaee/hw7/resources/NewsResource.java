package org.examples.pbk.otus.javaee.hw7.resources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.security.PermitAll;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("news")
@Produces(MediaType.APPLICATION_JSON)
public class NewsResource {

    @GET
    @PermitAll
    public Response getAll() throws Exception {
        Document site = Jsoup.connect("https://www.gazeta.ru/").get();
        Elements newsElements = site.getElementById("headlines").getElementsByClass("sausage-list-item");

        JsonArrayBuilder newsArray = Json.createArrayBuilder();
        for (Element element : newsElements) {
            Element aTag = element.getElementsByTag("a").last();
            JsonObjectBuilder article = Json.createObjectBuilder();
            article.add("text", aTag.text());
            article.add("ref", aTag.absUrl("href"));
            newsArray.add(article.build());
        }

        JsonObject root = Json.createObjectBuilder().add("news", newsArray.build()).build();

        return Response.ok(root).build();
    }
}
