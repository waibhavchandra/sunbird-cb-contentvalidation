package org.sunbird.contentvalidation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageProfanity {
    private boolean is_safe;
    @JsonProperty("nsfw-nude")
    private int nsfwNude;
    @JsonProperty("nsfw-risque")
    private int nsfwRisque;
    @JsonProperty("nsfw-sex")
    private int nsfwSex;
    @JsonProperty("nsfw-violence")
    private int nsfwViolence;
    private int sfw;

    public boolean isIs_safe() {
        return is_safe;
    }

    public void setIs_safe(boolean is_safe) {
        this.is_safe = is_safe;
    }

    public int getNsfwNude() {
        return nsfwNude;
    }

    public void setNsfwNude(int nsfwNude) {
        this.nsfwNude = nsfwNude;
    }

    public int getNsfwRisque() {
        return nsfwRisque;
    }

    public void setNsfwRisque(int nsfwRisque) {
        this.nsfwRisque = nsfwRisque;
    }

    public int getNsfwSex() {
        return nsfwSex;
    }

    public void setNsfwSex(int nsfwSex) {
        this.nsfwSex = nsfwSex;
    }

    public int getNsfwViolence() {
        return nsfwViolence;
    }

    public void setNsfwViolence(int nsfwViolence) {
        this.nsfwViolence = nsfwViolence;
    }

    public int getSfw() {
        return sfw;
    }

    public void setSfw(int sfw) {
        this.sfw = sfw;
    }
}
