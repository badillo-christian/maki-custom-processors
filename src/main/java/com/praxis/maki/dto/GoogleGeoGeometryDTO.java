package com.praxis.maki.dto;

public class GoogleGeoGeometryDTO {
    private GoogleGeoBoundsDTO bounds;
    private GoogleGeoLatLngDTO location;
    private String location_type;
    private GoogleGeoBoundsDTO viewport;

    public GoogleGeoBoundsDTO getBounds() {
        return bounds;
    }

    public void setBounds(GoogleGeoBoundsDTO bounds) {
        this.bounds = bounds;
    }

    public GoogleGeoLatLngDTO getLocation() {
        return location;
    }

    public void setLocation(GoogleGeoLatLngDTO location) {
        this.location = location;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public GoogleGeoBoundsDTO getViewport() {
        return viewport;
    }

    public void setViewport(GoogleGeoBoundsDTO viewport) {
        this.viewport = viewport;
    }
}