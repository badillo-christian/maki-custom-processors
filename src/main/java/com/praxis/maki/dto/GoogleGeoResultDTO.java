package com.praxis.maki.dto;

public class GoogleGeoResultDTO {
    private GoogleGeoAdressComponentDTO [] address_components;
    private String formatted_address;
    private GoogleGeoGeometryDTO geometry;
    private Boolean partial_match;
    private String place_id;
    private String [] types;

    public GoogleGeoAdressComponentDTO[] getAddress_components() {
        return address_components;
    }

    public void setAddress_components(GoogleGeoAdressComponentDTO[] address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GoogleGeoGeometryDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleGeoGeometryDTO geometry) {
        this.geometry = geometry;
    }

    public Boolean getPartial_match() {
        return partial_match;
    }

    public void setPartial_match(Boolean partial_match) {
        this.partial_match = partial_match;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}