package com.ils.data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

/**
 * Created by mara on 7/12/15.
 */
@Entity
public class PackageStateLink extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne(cascade = {REFRESH, PERSIST, MERGE})
    @JoinColumn(name="ilsPackage_id")
    private IlsPackage ilsPackage;

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

    public IlsPackage getIlsPackage() {
        return ilsPackage;
    }

    public void setIlsPackage(IlsPackage ilsPackage) {
        this.ilsPackage = ilsPackage;
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
}
