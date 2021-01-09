package com.sequencegenerator.app.request;

import com.sequencegenerator.app.constant.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SequenceGenerationRequest {

    @NotNull(message = Constants.ERROR_NOT_NULL)
    @NotBlank(message = Constants.ERROR_NOT_BLANK)
    @Pattern(regexp = "[0-9]+", message = Constants.ERROR_INVALID_INPUT)
    private String goal;

    @NotNull(message = Constants.ERROR_NOT_NULL)
    @NotBlank(message = Constants.ERROR_NOT_BLANK)
    @Pattern(regexp = "[0-9]+", message = Constants.ERROR_INVALID_INPUT)
    private String step;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
