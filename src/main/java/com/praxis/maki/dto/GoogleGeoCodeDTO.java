package com.praxis.maki.dto;

public class GoogleGeoCodeDTO {
    private String status;
    private GoogleGeoResultDTO [] results;
    private Boolean exclude_from_slo;
    private String error_message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GoogleGeoResultDTO[] getResults() {
        return results;
    }

    public void setResults(GoogleGeoResultDTO[] results) {
        this.results = results;
    }

    public Boolean getExclude_from_slo() {
        return exclude_from_slo;
    }

    public void setExclude_from_slo(Boolean exclude_from_slo) {
        this.exclude_from_slo = exclude_from_slo;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}