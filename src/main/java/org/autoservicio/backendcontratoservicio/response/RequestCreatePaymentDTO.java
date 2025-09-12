package org.autoservicio.backendcontratoservicio.response;

public class RequestCreatePaymentDTO {
    private Number amount;
    private String currency;
    private String orderId;
    private CustomerDTO customer;


    public Number getAmount() {
        return amount;
    }


    public void setAmount(Number amount) {
        this.amount = amount;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getOrderId() {
        return orderId;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public CustomerDTO getCustomer() {
        return customer;
    }


    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }


    public class CustomerDTO {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


    }
}
