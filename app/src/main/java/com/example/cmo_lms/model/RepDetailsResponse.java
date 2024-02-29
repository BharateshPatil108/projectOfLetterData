package com.example.cmo_lms.model;

import com.google.gson.annotations.SerializedName;

public class RepDetailsResponse {
    @SerializedName("rep_name")
    private String repName;

    @SerializedName("con_name")
    private String conName;

    @SerializedName("categ_name")
    private String categoryName;

    @SerializedName("dept_name")
    private String deptName;

    @SerializedName("line_dep_name")
    private String lineDepName;

    @SerializedName("post_name")
    private String postName;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    // Getters and setters
    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLineDepName() {
        return lineDepName;
    }

    public void setLineDepName(String lineDepName) {
        this.lineDepName = lineDepName;
    }

    @Override
    public String toString() {
        return "RepDetailsResponse{" + "repName='" + repName + '\'' + ", conName='" + conName + '\'' + ", categoryName='" + categoryName + '\'' + ", deptName='" + deptName + '\'' + ", lineDepName='" + lineDepName + '\'' + '}';
    }

}
