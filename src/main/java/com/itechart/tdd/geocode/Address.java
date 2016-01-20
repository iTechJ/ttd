package com.itechart.tdd.geocode;

public class Address {

    private String placeId;
    private String fullAddress;
    private String country;
    private String city;
    private String street;
    private String streetNumber;

    private Double lat;
    private Double lng;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Address {\n" +
                " placeId = '" + placeId + "\',\n" +
                " fullAddress = '" + fullAddress + "\',\n" +
                " country = '" + country + "\',\n" +
                " city = '" + city + "\',\n" +
                " street = '" + street + "\',\n" +
                " streetNumber = '" + streetNumber + "\',\n" +
                " lat = " + lat + ",\n" +
                " lng = " + lng + "\n" +
                '}';
    }
}
