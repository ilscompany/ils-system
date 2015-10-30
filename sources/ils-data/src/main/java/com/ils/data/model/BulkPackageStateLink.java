package com.ils.data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

/**
 * Created by mara on 7/12/15.
 */
@Entity
public class BulkPackageStateLink extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE})
    @JoinColumn(name="bulkPackage_id")
    private BulkPackage bulkPackage;

    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE})
    @JoinColumn(name="state_id")
    private State state;

    @Column(nullable=false, updatable=false, insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp creationDate;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BulkPackage getBulkPackage() {
        return bulkPackage;
    }

    public void setBulkPackage(BulkPackage bulkPackage) {
        this.bulkPackage = bulkPackage;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BulkPackageStateLink)) return false;
        BulkPackageStateLink that = (BulkPackageStateLink) o;
        return Objects.equals(getBulkPackage(), that.getBulkPackage()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getCreationDate(), that.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBulkPackage(), getState(), getCreationDate());
    }
}
