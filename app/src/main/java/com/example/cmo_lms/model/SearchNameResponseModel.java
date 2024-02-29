package com.example.cmo_lms.model;

import java.io.Serializable;

public class SearchNameResponseModel implements Serializable {
    private String mp_name;
    private String rg_ref_no;
    private String mp_constituency_desc;
    private String rg_grievance_desc;
    private String department_name;
    private int rg_no_days;
    private String cc_name;
    private String cc_kname;
    private int eOffice_recipt_cmp_no;
    private String eOffice_status;
    private String rg_cm_remark;
    private String eoffice_closing_remarks;
    private String officer_name;
    private long mobile_number;
    private String post_desc;
    private int repType;
    private String rgDate;
    private int rgPriority;
    private String categoryDescription;
    private String eofficeReceiptNo;
    private String eofficeCurrentlyWith;
    private int eofficeSinceWhen;
    private String rgCreatedOn;

    public int getRepType() {
        return repType;
    }

    public void setRepType(int repType) {
        this.repType = repType;
    }

    public String getRgDate() {
        return rgDate;
    }

    public void setRgDate(String rgDate) {
        this.rgDate = rgDate;
    }

    public int getRgPriority() {
        return rgPriority;
    }

    public void setRgPriority(int rgPriority) {
        this.rgPriority = rgPriority;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getEofficeReceiptNo() {
        return eofficeReceiptNo;
    }

    public void setEofficeReceiptNo(String eofficeReceiptNo) {
        this.eofficeReceiptNo = eofficeReceiptNo;
    }

    public String getEofficeCurrentlyWith() {
        return eofficeCurrentlyWith;
    }

    public void setEofficeCurrentlyWith(String eofficeCurrentlyWith) {
        this.eofficeCurrentlyWith = eofficeCurrentlyWith;
    }

    public int getEofficeSinceWhen() {
        return eofficeSinceWhen;
    }

    public void setEofficeSinceWhen(int eofficeSinceWhen) {
        this.eofficeSinceWhen = eofficeSinceWhen;
    }

    public String getRgCreatedOn() {
        return rgCreatedOn;
    }

    public void setRgCreatedOn(String rgCreatedOn) {
        this.rgCreatedOn = rgCreatedOn;
    }

    public String getMp_name() {
        return mp_name;
    }

    public void setMp_name(String mp_name) {
        this.mp_name = mp_name;
    }

    public String getRg_ref_no() {
        return rg_ref_no;
    }

    public void setRg_ref_no(String rg_ref_no) {
        this.rg_ref_no = rg_ref_no;
    }

    public String getMp_constituency_desc() {
        return mp_constituency_desc;
    }

    public void setMp_constituency_desc(String mp_constituency_desc) {
        this.mp_constituency_desc = mp_constituency_desc;
    }

    public String getRg_grievance_desc() {
        return rg_grievance_desc;
    }

    public void setRg_grievance_desc(String rg_grievance_desc) {
        this.rg_grievance_desc = rg_grievance_desc;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getRg_no_days() {
        return rg_no_days;
    }

    public void setRg_no_days(int rg_no_days) {
        this.rg_no_days = rg_no_days;
    }

    public String getCc_name() {
        return cc_name;
    }

    public void setCc_name(String cc_name) {
        this.cc_name = cc_name;
    }

    public String getCc_kname() {
        return cc_kname;
    }

    public void setCc_kname(String cc_kname) {
        this.cc_kname = cc_kname;
    }

    public int geteOffice_recipt_cmp_no() {
        return eOffice_recipt_cmp_no;
    }

    public void seteOffice_recipt_cmp_no(int eOffice_recipt_cmp_no) {
        this.eOffice_recipt_cmp_no = eOffice_recipt_cmp_no;
    }

    public String geteOffice_status() {
        return eOffice_status;
    }

    public void seteOffice_status(String eOffice_status) {
        this.eOffice_status = eOffice_status;
    }

    public String getRg_cm_remark() {
        return rg_cm_remark;
    }

    public void setRg_cm_remark(String rg_cm_remark) {
        this.rg_cm_remark = rg_cm_remark;
    }

    public String getEoffice_closing_remarks() {
        return eoffice_closing_remarks;
    }

    public void setEoffice_closing_remarks(String eoffice_closing_remarks) {
        this.eoffice_closing_remarks = eoffice_closing_remarks;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public long getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(long mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }
}
