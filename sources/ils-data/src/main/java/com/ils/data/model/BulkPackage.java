package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import static javax.persistence.CascadeType.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mara on 7/11/15.
 */
@Entity
public class BulkPackage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String identifier;

    @OneToOne(cascade=ALL)
    @JoinColumn(name="shippingFee_id")
    private Payment shippingFee;

    @OneToOne(cascade=ALL)
    @JoinColumn(name="clearanceFees_id")
    private Payment clearanceFees;

    @OneToMany(mappedBy="bulkPackage", cascade=ALL, fetch = FetchType.EAGER)
    private List<BulkPackageStateLink> stateLinkList = new ArrayList<BulkPackageStateLink>();

    @OneToMany(mappedBy="bulkPackage")
    private List<IlsPackage> ilsPackageList = new ArrayList<IlsPackage>();

    public BulkPackage(){
        this.identifier = createNewIdentifier();
    }

    private Timestamp expectedArrivalDate;

    private double weight;

    private String comments;

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

    public Payment getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Payment shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Payment getClearanceFees() {
        return clearanceFees;
    }

    public void setClearanceFees(Payment clearanceFees) {
        this.clearanceFees = clearanceFees;
    }

    public void addStateLinkList(BulkPackageStateLink link) {
        if (link != null){
            stateLinkList.add(link);
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(Timestamp expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public void setIlsPackageList(List<IlsPackage> ilsPackageList) {
        this.ilsPackageList = ilsPackageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BulkPackage)) return false;
        BulkPackage that = (BulkPackage) o;
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
                .append("shippingFee", shippingFee)
                .append("clearanceFees", clearanceFees)
                .append("ilsPackageList", ilsPackageList)
                .append("expectedArrivalDate", expectedArrivalDate)
                .append("weight", weight)
                .append("comments", comments)
                .toString();
    }
}
