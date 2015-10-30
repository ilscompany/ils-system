package com.ils.core.notify.impl;

import java.util.Objects;

/**
 * Created by mara on 10/25/15.
 */
public class UserNotifyMessage {

    /**
     * the email of the user to notify
     */
    private String email;

    /**
     * The phone number of the user to notify
     */
    private String phone;

    /**
     * country code of the user receiving notification message
     * this attribute is only useful for sms notification
     */
    private String countryCode;

    /**
     * The message to send
     */
    private String text;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserNotifyMessage)) return false;
        UserNotifyMessage that = (UserNotifyMessage) o;
        return Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getCountryCode(), that.getCountryCode()) &&
                Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPhone(), getCountryCode(), getText());
    }
}
