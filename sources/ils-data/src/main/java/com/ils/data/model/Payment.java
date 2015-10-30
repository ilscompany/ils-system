package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

/**
 * Created by mara on 7/11/15.
 */
@Entity
public class Payment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH})
    @JoinColumn(name="paymentMethod_id")
    private PaymentMethod paymentMethod;

    private double price;

    private float discount;

    private double paid;

    private String comments;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getPrice(), payment.getPrice()) &&
                Objects.equals(getDiscount(), payment.getDiscount()) &&
                Objects.equals(getPaid(), payment.getPaid()) &&
                Objects.equals(getPaymentMethod(), payment.getPaymentMethod()) &&
                Objects.equals(getComments(), payment.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentMethod(), getPrice(), getDiscount(), getPaid(), getComments());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("paymentMethod", paymentMethod)
                .append("price", price)
                .append("discount", discount)
                .append("paid", paid)
                .append("comments", comments)
                .toString();
    }
}
