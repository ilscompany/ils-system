package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import static javax.persistence.CascadeType.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mara on 7/12/15.
 */
@Entity
public class IlsPackage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String identifier;

    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private IlsUser sender;

    @ManyToOne
    @JoinColumn(name="recipient_id", nullable = false)
    private IlsUser recipient;

    @ManyToOne
    @JoinColumn(name="bulkPackage_id")
    private BulkPackage bulkPackage;

    @OneToOne
    @JoinColumn(name="payment_id")
    private Payment payment;

    @OneToMany(mappedBy="ilsPackage", cascade=ALL, fetch = FetchType.EAGER)
    private List<PackageStateLink> stateLinkList = new ArrayList<PackageStateLink>();

    private double weight;

    private String description;

    private String comments;

    public IlsPackage(){
        this.identifier = createNewIdentifier();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public IlsUser getSender() {
        return sender;
    }

    public void setSender(IlsUser sender) {
        this.sender = sender;
    }

    public IlsUser getRecipient() {
        return recipient;
    }

    public void setRecipient(IlsUser recipient) {
        this.recipient = recipient;
    }

    public BulkPackage getBulkPackage() {
        return bulkPackage;
    }

    public void setBulkPackage(BulkPackage bulkPackage) {
        this.bulkPackage = bulkPackage;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void addStateLink(PackageStateLink link) {
        if (link != null) {
            stateLinkList.add(link);
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof IlsPackage)) return false;
        IlsPackage that = (IlsPackage) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identifier", identifier)
                .append("sender", sender)
                .append("recipient", recipient)
                .append("bulkPackage", bulkPackage)
                .append("payment", payment)
                .append("weight", weight)
                .append("description", description)
                .append("comments", comments)
                .toString();
    }
}
