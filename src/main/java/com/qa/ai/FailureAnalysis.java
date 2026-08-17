package com.qa.ai;

import java.util.ArrayList;
import java.util.List;

public class FailureAnalysis {

    private String failureCategory;

    private List<String> possibleRootCauses = new ArrayList<>();

    private List<String> solutions = new ArrayList<>();

    public String getFailureCategory() {
        return failureCategory;
    }

    public void setFailureCategory(String failureCategory) {
        this.failureCategory = failureCategory;
    }

    public List<String> getPossibleRootCauses() {
        return possibleRootCauses;
    }

    public void setPossibleRootCauses(List<String> possibleRootCauses) {
        this.possibleRootCauses = possibleRootCauses;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }
}