package com.jocata.userservice.form;

public class UserAddressForm {
    private String id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;
    private String createdAt;
    private String updatedAt;
    private String userId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
