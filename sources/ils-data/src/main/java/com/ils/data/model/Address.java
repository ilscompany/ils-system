package com.ils.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by mara on 7/11/15.
 */
@Entity
public class Address extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String address;

    private String additionalAddress;

    private int zipcode;

    private String city;

    private String country;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address1 = (Address) o;
        return Objects.equals(getZipcode(), address1.getZipcode()) &&
                Objects.equals(getAddress(), address1.getAddress()) &&
                Objects.equals(getAdditionalAddress(), address1.getAdditionalAddress()) &&
                Objects.equals(getCity(), address1.getCity()) &&
                Objects.equals(getCountry(), address1.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getAdditionalAddress(), getZipcode(), getCity(), getCountry());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", additionalAddress='" + additionalAddress + '\'' +
                ", zipcode=" + zipcode +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
