package com.example.apptracuuphim.model.SocialMedia;

public class Media {
    private String name;
    private String external_id;
    private int logo;

    public Media(String name, String external_id, int logo) {
        this.name = name;
        this.external_id = external_id;
        this.logo = logo;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
