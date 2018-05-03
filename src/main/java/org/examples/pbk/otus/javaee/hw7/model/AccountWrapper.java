package org.examples.pbk.otus.javaee.hw7.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement (name = "accounts")
public class AccountWrapper {
    private List<Account> accounts;

    @XmlElement (name = "account", required = true)
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
