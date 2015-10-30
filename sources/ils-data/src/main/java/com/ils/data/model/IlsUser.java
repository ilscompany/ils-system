package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

/**
 * Created by mara on 7/12/15.
 */
@Entity
public class IlsUser extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @OneToMany(mappedBy="sender")
    private List<IlsPackage> packagesSent = new ArrayList<IlsPackage>();

    @OneToMany(mappedBy="recipient")
    private List<IlsPackage> packagesReceived = new ArrayList<IlsPackage>();

    private String firstname;

    private String lastname;

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, length=40)
	private String password;
    
    @Column(nullable = false, unique = true)
    private String phone;

    @Email
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch=EAGER)
	@JoinTable(name="user_role")
    private List<Role> authorities = new ArrayList<Role>();
    
    @OneToOne(cascade=ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IlsUser)) return false;
        IlsUser ilsUser = (IlsUser) o;
        return Objects.equals(getFirstname(), ilsUser.getFirstname()) &&
                Objects.equals(getLastname(), ilsUser.getLastname()) &&
                Objects.equals(getUsername(), ilsUser.getUsername()) &&
                Objects.equals(getPhone(), ilsUser.getPhone()) &&
                Objects.equals(getEmail(), ilsUser.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getUsername(), getPhone(), getEmail());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstname", firstname)
                .append("lastname", lastname)
                .append("username", username)
                .append("phone", phone)
                .append("email", email)
                .toString();
    }
}
