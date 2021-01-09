package com.sequencegenerator.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskSequenceResponse {

    private String result;

    private List<String> results;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public TaskSequenceResponse(String result) {
        this.result = result;
    }

    public TaskSequenceResponse(List<String> results) {
        this.results = results;
    }

}
