package com.praxis.maki.dto;

public class GoogleGeoBoundsDTO{
    private GoogleGeoLatLngDTO northeast;
    private GoogleGeoLatLngDTO southwest;

    public GoogleGeoLatLngDTO getNortheast() {
        return northeast;
    }

    public void setNortheast(GoogleGeoLatLngDTO northeast) {
        this.northeast = northeast;
    }

    public GoogleGeoLatLngDTO getSouthwest() {
        return southwest;
    }

    public void setSouthwest(GoogleGeoLatLngDTO southwest) {
        this.southwest = southwest;
    }
}