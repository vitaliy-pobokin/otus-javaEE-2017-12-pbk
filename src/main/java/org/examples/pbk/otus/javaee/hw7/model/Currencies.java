package org.examples.pbk.otus.javaee.hw7.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ValCurs")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Currencies {
    @JacksonXmlProperty(localName = "Valute")
    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "currencies=" + currencies +
                '}';
    }
}
