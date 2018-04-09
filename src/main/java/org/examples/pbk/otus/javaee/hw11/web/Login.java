package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.ejb.CustomerJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Customer;
import org.examples.pbk.otus.javaee.hw11.exception.AuthenticationException;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("login")
@SessionScoped
public class Login implements Serializable {

    @EJB
    private CustomerJpaBean customerBean;

    @Inject
    private Error errorBean;

    private Customer authenticatedCustomer;

    public String login(String username, String password) {
        try {
            Customer customer = customerBean.findByUsername(username);
            if (customer != null) {
                checkCredentials(customer, password);
                this.authenticatedCustomer = customer;
                return "index.xhtml?faces-redirect=true";
            }
        } catch (JpaBeanException | AuthenticationException e) {
            errorBean.setMessage(e.getMessage());
        }
        return "error.xhtml";
    }

    public String logout() {
        this.authenticatedCustomer = null;
        return "index.xhtml?faces-redirect=true";
    }

    public String register(String username, String password, String address) {
        Customer customer = new Customer(username, password, address);
        try {
            customerBean.createCustomer(customer);
        } catch (Exception e) {
            errorBean.setMessage(e.getMessage());
            return "error.xhtml";
        }
        return "index.xhtml?faces-redirect=true";
    }

    public Customer getAuthenticatedCustomer() {
        return authenticatedCustomer;
    }

    public boolean isCustomerAuthenticated() {
        return authenticatedCustomer != null;
    }

    private void checkCredentials(Customer customer, String password) throws AuthenticationException {
        if (customer.getPassword().equals(password)) {
            return;
        }
        throw new AuthenticationException("Authentication exception");
    }
}
