package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by mara on 7/11/15.
 */
@Entity
public class State extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="state")
    private List<BulkPackageStateLink> bulkStateLinkList;

    @OneToMany(mappedBy="state")
    private List<PackageStateLink> packageStateLinkList;

    private String value;

    private String description;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return Objects.equals(getValue(), state.getValue()) &&
                Objects.equals(getDescription(), state.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getDescription());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", description)
                .append("value", value)
                .toString();
    }
}
