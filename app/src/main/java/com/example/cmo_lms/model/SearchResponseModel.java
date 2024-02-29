package com.example.cmo_lms.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchResponseModel implements Serializable {
    @SerializedName("rg_ref")
    private String rgRef;

    @SerializedName("rep_type")
    private int repType;

    @SerializedName("rg_date")
    private String rgDate;

    @SerializedName("mla_constituency_desc")
    private String mlaConstituencyDesc;

    @SerializedName("mla_names")
    private String mlaNames;

    @SerializedName("mladis")
    private String mladis;

    @SerializedName("mp_constituency_desc")
    private String mpConstituencyDesc;

    @SerializedName("mp_name")
    private String mpName;

    @SerializedName("rg_address1")
    private String rgAddress1;

    @SerializedName("rg_griv_category_id")
    private int rgGrivCategoryId;

    @SerializedName("rg_cmog_action_type")
    private int rgCmogActionType;

    @SerializedName("rg_grievance_desc")
    private String rgGrievanceDesc;

    @SerializedName("department_name")
    private String departmentName;

    @SerializedName("district_name")
    private String districtName;

    @SerializedName("mlc_member_name")
    private String mlcMemberName;

    @SerializedName("mp_member_name")
    private String mpMemberName;

    @SerializedName("em_kname")
    private String emKname;

    @SerializedName("rg_priority")
    private int rgPriority;

    @SerializedName("rg_no_days")
    private int rgNoDays;

    @SerializedName("category_description")
    private String categoryDescription;

    @SerializedName("cc_name")
    private String ccName;

    @SerializedName("cc_kname")
    private String ccKname;

    @SerializedName("eoffice_recipt_cmp_no")
    private int eofficeReciptCmpNo;

    @SerializedName("eoffice_status")
    private String eofficeStatus;

    @SerializedName("eoffice_receipt_no")
    private String eofficeReceiptNo;

    @SerializedName("rg_cm_remark")
    private String rgCmRemark;

    @SerializedName("eoffice_closing_remarks")
    private String eofficeClosingRemarks;

    @SerializedName("eoffice_currenetly_with")
    private String eofficeCurrentlyWith;

    @SerializedName("eoffice_since_when")
    private int eofficeSinceWhen;

    @SerializedName("rg_created_on")
    private String rgCreatedOn;

    @SerializedName("officer_name")
    private String officerName;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("post_desc")
    private String postDesc;

    public String getRgRef() {
        return rgRef;
    }

    public void setRgRef(String rgRef) {
        this.rgRef = rgRef;
    }

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

    public String getMlaConstituencyDesc() {
        return mlaConstituencyDesc;
    }

    public void setMlaConstituencyDesc(String mlaConstituencyDesc) {
        this.mlaConstituencyDesc = mlaConstituencyDesc;
    }

    public String getMlaNames() {
        return mlaNames;
    }

    public void setMlaNames(String mlaNames) {
        this.mlaNames = mlaNames;
    }

    public String getMladis() {
        return mladis;
    }

    public void setMladis(String mladis) {
        this.mladis = mladis;
    }

    public String getMpConstituencyDesc() {
        return mpConstituencyDesc;
    }

    public void setMpConstituencyDesc(String mpConstituencyDesc) {
        this.mpConstituencyDesc = mpConstituencyDesc;
    }

    public String getMpName() {
        return mpName;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public String getRgAddress1() {
        return rgAddress1;
    }

    public void setRgAddress1(String rgAddress1) {
        this.rgAddress1 = rgAddress1;
    }

    public int getRgGrivCategoryId() {
        return rgGrivCategoryId;
    }

    public void setRgGrivCategoryId(int rgGrivCategoryId) {
        this.rgGrivCategoryId = rgGrivCategoryId;
    }

    public int getRgCmogActionType() {
        return rgCmogActionType;
    }

    public void setRgCmogActionType(int rgCmogActionType) {
        this.rgCmogActionType = rgCmogActionType;
    }

    public String getRgGrievanceDesc() {
        return rgGrievanceDesc;
    }

    public void setRgGrievanceDesc(String rgGrievanceDesc) {
        this.rgGrievanceDesc = rgGrievanceDesc;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMlcMemberName() {
        return mlcMemberName;
    }

    public void setMlcMemberName(String mlcMemberName) {
        this.mlcMemberName = mlcMemberName;
    }

    public String getMpMemberName() {
        return mpMemberName;
    }

    public void setMpMemberName(String mpMemberName) {
        this.mpMemberName = mpMemberName;
    }

    public String getEmKname() {
        return emKname;
    }

    public void setEmKname(String emKname) {
        this.emKname = emKname;
    }

    public int getRgPriority() {
        return rgPriority;
    }

    public void setRgPriority(int rgPriority) {
        this.rgPriority = rgPriority;
    }

    public int getRgNoDays() {
        return rgNoDays;
    }

    public void setRgNoDays(int rgNoDays) {
        this.rgNoDays = rgNoDays;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcKname() {
        return ccKname;
    }

    public void setCcKname(String ccKname) {
        this.ccKname = ccKname;
    }

    public int getEofficeReciptCmpNo() {
        return eofficeReciptCmpNo;
    }

    public void setEofficeReciptCmpNo(int eofficeReciptCmpNo) {
        this.eofficeReciptCmpNo = eofficeReciptCmpNo;
    }

    public String getEofficeStatus() {
        return eofficeStatus;
    }

    public void setEofficeStatus(String eofficeStatus) {
        this.eofficeStatus = eofficeStatus;
    }

    public String getEofficeReceiptNo() {
        return eofficeReceiptNo;
    }

    public void setEofficeReceiptNo(String eofficeReceiptNo) {
        this.eofficeReceiptNo = eofficeReceiptNo;
    }

    public String getRgCmRemark() {
        return rgCmRemark;
    }

    public void setRgCmRemark(String rgCmRemark) {
        this.rgCmRemark = rgCmRemark;
    }

    public String getEofficeClosingRemarks() {
        return eofficeClosingRemarks;
    }

    public void setEofficeClosingRemarks(String eofficeClosingRemarks) {
        this.eofficeClosingRemarks = eofficeClosingRemarks;
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

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }
}