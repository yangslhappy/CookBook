package com.example.model;

import java.io.Serializable;

/**
 * Created by 10734 on 2018/4/20 0020.
 */

public class CookStep implements Serializable {

    private String step;
    private String stepimg;
    private String stepintro;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStepimg() {
        return stepimg;
    }

    public void setStepimg(String stepimg) {
        this.stepimg = stepimg;
    }

    public String getStepintro() {
        return stepintro;
    }

    public void setStepintro(String stepintro) {
        this.stepintro = stepintro;
    }

    public CookStep(String step, String stepimg, String stepintro) {
        this.step = step;
        this.stepimg = stepimg;
        this.stepintro = stepintro;
    }
}
