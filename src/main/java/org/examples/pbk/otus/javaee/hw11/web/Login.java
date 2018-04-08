package org.examples.pbk.otus.javaee.hw11.web;

import org.examples.pbk.otus.javaee.hw11.ejb.CustomerJpaBean;
import org.examples.pbk.otus.javaee.hw11.entity.Customer;
import org.examples.pbk.otus.javaee.hw11.exception.JpaBeanException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Named("login")
@SessionScoped
public class Login implements Serializable {

    @EJB
    private CustomerJpaBean customerBean;

    private Customer authenticatedCustomer;

    public String login(String username, String password) {
        try {
            Customer customer = customerBean.findByUsername(username);
            if (customer != null && checkCredentials(customer, password)) {
//                setCustomerCookie(customer);
                this.authenticatedCustomer = customer;
                return "index.xhtml?faces-redirect=true";
            }
        } catch (JpaBeanException e) {
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

    private boolean checkCredentials(Customer customer, String password) {
        return customer.getPassword().equals(password);
    }

    /*private void setCustomerCookie(Customer customer) throws UnsupportedEncodingException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String name = "authenticated_as";
        String value = String.valueOf(customer.getUserId());
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        context.addResponseCookie(name, URLEncoder.encode(value, "UTF-8"), properties);
    }*/
}
