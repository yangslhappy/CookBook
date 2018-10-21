package com.example.model;

import java.util.List;

/**
 * Created by 10734 on 2018/4/20 0020.
 */

public class CookDetails {

    private String cookName;
    private String cookimg;
    private String cookintro;
    private String cookingredients;
    private String cookburden;
    private List<CookStep> cookSteps;

    public String getCookimg() {
        return cookimg;
    }

    public void setCookimg(String cookimg) {
        this.cookimg = cookimg;
    }

    public String getCookintro() {
        return cookintro;
    }

    public void setCookintro(String cookintro) {
        this.cookintro = cookintro;
    }

    public String getCookingredients() {
        return cookingredients;
    }

    public void setCookingredients(String cookingredients) {
        this.cookingredients = cookingredients;
    }

    public String getCookburden() {
        return cookburden;
    }

    public void setCookburden(String cookburden) {
        this.cookburden = cookburden;
    }

    public List<CookStep> getCookStep() {
        return cookSteps;
    }

    public void setCookStep(List<CookStep> cookSteps) {
        this.cookSteps = cookSteps;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public CookDetails(String cookName, String cookimg, String cookintro, String cookingredients, String cookburden, List<CookStep> cookSteps) {
        this.cookName = cookName;
        this.cookimg = cookimg;
        this.cookintro = cookintro;
        this.cookingredients = cookingredients;
        this.cookburden = cookburden;
        this.cookSteps = cookSteps;
    }
}
