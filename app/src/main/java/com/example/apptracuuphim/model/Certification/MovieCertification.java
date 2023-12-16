package com.example.apptracuuphim.model.Certification;

import java.util.List;

public class MovieCertification {
    protected String iso_3166_1;
    protected List<Certification> release_dates;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public List<Certification> getRelease_dates() {
        return release_dates;
    }

    public void setRelease_dates(List<Certification> release_dates) {
        this.release_dates = release_dates;
    }
}
