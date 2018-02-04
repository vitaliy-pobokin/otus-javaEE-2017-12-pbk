package org.examples.pbk.otus.javaee.hw4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/api/news")
public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Document site = Jsoup.connect("https://www.gazeta.ru/").get();
        Elements news = site.getElementById("headlines").getElementsByClass("sausage-list-item");

        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Element element : news) {
            Element aTag = element.getElementsByTag("a").last();
            JsonObjectBuilder newsObj = Json.createObjectBuilder();
            newsObj.add("text", aTag.text());
            newsObj.add("ref", aTag.absUrl("href"));
            jsonArray.add(newsObj.build());
        }

        JsonObject root = Json.createObjectBuilder().add("news", jsonArray.build()).build();

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(root.toString());
    }
}
