package com.example.cmo_lms.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Searchref_noResponseModel implements Serializable {
    @SerializedName("rg_id")
    private int rgId;
    @SerializedName("rg_ref")
    private String rgRef;
    @SerializedName("rg_date")
    private String rgDate;
    @SerializedName("rg_letter_no")
    private String rgLetterNo;
    @SerializedName("rg_representative_id")
    private Integer rgRepresentativeId;
    @SerializedName("rg_representative_mob")
    private String rgRepresentativeMob;
    @SerializedName("rg_address1")
    private String rgAddress1;
    @SerializedName("rg_griv_category_id")
    private int rgGrivCategoryId;
    @SerializedName("rg_attachement_path")
    private String rgAttachementPath;
    @SerializedName("re_cm_note_path")
    private String reCmNotePath;
    @SerializedName("rg_grievance_desc")
    private String rgGrievanceDesc;
    @SerializedName("rg_forwarded_dept_id")
    private Integer rgForwardedDeptId;
    @SerializedName("rg_forwarded_linedepartment_id")
    private Integer rgForwardedLinedepartmentId;
    @SerializedName("rg_forwarded_district_id")
    private Integer rgForwardedDistrictId;
    @SerializedName("rg_cmog_action_type")
    private int rgCmogActionType;
    @SerializedName("rg_closure_date")
    private String rgClosureDate;
    @SerializedName("rg_closure_description")
    private String rgClosureDescription;
    @SerializedName("rg_post_id")
    private int rgPostId;
    @SerializedName("rg_is_atr_filled")
    private boolean rgIsAtrFilled;
    @SerializedName("rg_is_active")
    private boolean rgIsActive;
    @SerializedName("rg_created_on")
    private String rgCreatedOn;
    @SerializedName("rg_created_by")
    private int rgCreatedBy;
    @SerializedName("rg_updated_on")
    private String rgUpdatedOn;
    @SerializedName("rg_updated_by")
    private Integer rgUpdatedBy;
    @SerializedName("rep_type")
    private int repType;
    @SerializedName("mla_constituency")
    private Integer mlaConstituency;
    @SerializedName("mpl_constituency")
    private Integer mplConstituency;
    @SerializedName("mpr_constituency")
    private Integer mprConstituency;
    @SerializedName("ex_mlc_constituency")
    private Integer exMlcConstituency;
    @SerializedName("mlc_constituency")
    private Integer mlcConstituency;
    @SerializedName("rg_cm_remark")
    private String rgCmRemark;
    @SerializedName("rg_priority")
    private Integer rgPriority;
    @SerializedName("rg_no_days")
    private Integer rgNoDays;
    @SerializedName("eoffice_recipt_cmp_no")
    private Integer eofficeReciptCmpNo;
    @SerializedName("eoffice_status")
    private String eofficeStatus;
    @SerializedName("eoffice_receipt_no")
    private String eofficeReceiptNo;
    @SerializedName("eoffice_currenetly_with")
    private String eofficeCurrenetlyWith;
    @SerializedName("eoffice_since_when")
    private int eofficeSinceWhen;
    @SerializedName("eoffice_closing_remarks")
    private String eofficeClosingRemarks;
    @SerializedName("eoffice_filenumber")
    private String eofficeFilenumber;
    @SerializedName("eoffice_file_cmp_no")
    private String eofficeFileCmpNo;
    @SerializedName("eoffice_recipt_updated_on")
    private String eofficeReciptUpdatedOn;
    @SerializedName("eoffice_dep_code")
    private Integer eofficeDepCode;

    public int getRgId() {
        return rgId;
    }

    public void setRgId(int rgId) {
        this.rgId = rgId;
    }

    public String getRgRef() {
        return rgRef;
    }

    public void setRgRef(String rgRef) {
        this.rgRef = rgRef;
    }

    public String getRgDate() {
        return rgDate;
    }

    public void setRgDate(String rgDate) {
        this.rgDate = rgDate;
    }

    public String getRgLetterNo() {
        return rgLetterNo;
    }

    public void setRgLetterNo(String rgLetterNo) {
        this.rgLetterNo = rgLetterNo;
    }

    public Integer getRgRepresentativeId() {
        return rgRepresentativeId;
    }

    public void setRgRepresentativeId(Integer rgRepresentativeId) {
        this.rgRepresentativeId = rgRepresentativeId;
    }

    public String getRgRepresentativeMob() {
        return rgRepresentativeMob;
    }

    public void setRgRepresentativeMob(String rgRepresentativeMob) {
        this.rgRepresentativeMob = rgRepresentativeMob;
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

    public String getRgAttachementPath() {
        return rgAttachementPath;
    }

    public void setRgAttachementPath(String rgAttachementPath) {
        this.rgAttachementPath = rgAttachementPath;
    }

    public String getReCmNotePath() {
        return reCmNotePath;
    }

    public void setReCmNotePath(String reCmNotePath) {
        this.reCmNotePath = reCmNotePath;
    }

    public String getRgGrievanceDesc() {
        return rgGrievanceDesc;
    }

    public void setRgGrievanceDesc(String rgGrievanceDesc) {
        this.rgGrievanceDesc = rgGrievanceDesc;
    }

    public Integer getRgForwardedDeptId() {
        return rgForwardedDeptId;
    }

    public void setRgForwardedDeptId(Integer rgForwardedDeptId) {
        this.rgForwardedDeptId = rgForwardedDeptId;
    }

    public Integer getRgForwardedLinedepartmentId() {
        return rgForwardedLinedepartmentId;
    }

    public void setRgForwardedLinedepartmentId(Integer rgForwardedLinedepartmentId) {
        this.rgForwardedLinedepartmentId = rgForwardedLinedepartmentId;
    }

    public Integer getRgForwardedDistrictId() {
        return rgForwardedDistrictId;
    }

    public void setRgForwardedDistrictId(Integer rgForwardedDistrictId) {
        this.rgForwardedDistrictId = rgForwardedDistrictId;
    }

    public int getRgCmogActionType() {
        return rgCmogActionType;
    }

    public void setRgCmogActionType(int rgCmogActionType) {
        this.rgCmogActionType = rgCmogActionType;
    }

    public String getRgClosureDate() {
        return rgClosureDate;
    }

    public void setRgClosureDate(String rgClosureDate) {
        this.rgClosureDate = rgClosureDate;
    }

    public String getRgClosureDescription() {
        return rgClosureDescription;
    }

    public void setRgClosureDescription(String rgClosureDescription) {
        this.rgClosureDescription = rgClosureDescription;
    }

    public int getRgPostId() {
        return rgPostId;
    }

    public void setRgPostId(int rgPostId) {
        this.rgPostId = rgPostId;
    }

    public boolean getRgIsAtrFilled() {
        return rgIsAtrFilled;
    }

    public void setRgIsAtrFilled(boolean rgIsAtrFilled) {
        this.rgIsAtrFilled = rgIsAtrFilled;
    }

    public boolean getRgIsActive() {
        return rgIsActive;
    }

    public void setRgIsActive(boolean rgIsActive) {
        this.rgIsActive = rgIsActive;
    }

    public String getRgCreatedOn() {
        return rgCreatedOn;
    }

    public void setRgCreatedOn(String rgCreatedOn) {
        this.rgCreatedOn = rgCreatedOn;
    }

    public int getRgCreatedBy() {
        return rgCreatedBy;
    }

    public void setRgCreatedBy(int rgCreatedBy) {
        this.rgCreatedBy = rgCreatedBy;
    }

    public String getRgUpdatedOn() {
        return rgUpdatedOn;
    }

    public void setRgUpdatedOn(String rgUpdatedOn) {
        this.rgUpdatedOn = rgUpdatedOn;
    }

    public Integer getRgUpdatedBy() {
        return rgUpdatedBy;
    }

    public void setRgUpdatedBy(Integer rgUpdatedBy) {
        this.rgUpdatedBy = rgUpdatedBy;
    }

    public int getRepType() {
        return repType;
    }

    public void setRepType(int repType) {
        this.repType = repType;
    }

    public Integer getMlaConstituency() {
        return mlaConstituency;
    }

    public void setMlaConstituency(Integer mlaConstituency) {
        this.mlaConstituency = mlaConstituency;
    }

    public Integer getMplConstituency() {
        return mplConstituency;
    }

    public void setMplConstituency(Integer mplConstituency) {
        this.mplConstituency = mplConstituency;
    }

    public Integer getMprConstituency() {
        return mprConstituency;
    }

    public void setMprConstituency(Integer mprConstituency) {
        this.mprConstituency = mprConstituency;
    }

    public Integer getExMlcConstituency() {
        return exMlcConstituency;
    }

    public void setExMlcConstituency(Integer exMlcConstituency) {
        this.exMlcConstituency = exMlcConstituency;
    }

    public Integer getMlcConstituency() {
        return mlcConstituency;
    }

    public void setMlcConstituency(Integer mlcConstituency) {
        this.mlcConstituency = mlcConstituency;
    }

    public String getRgCmRemark() {
        return rgCmRemark;
    }

    public void setRgCmRemark(String rgCmRemark) {
        this.rgCmRemark = rgCmRemark;
    }

    public Integer getRgPriority() {
        return rgPriority;
    }

    public void setRgPriority(Integer rgPriority) {
        this.rgPriority = rgPriority;
    }

    public Integer getRgNoDays() {
        return rgNoDays;
    }

    public void setRgNoDays(Integer rgNoDays) {
        this.rgNoDays = rgNoDays;
    }

    public Integer getEofficeReciptCmpNo() {
        return eofficeReciptCmpNo;
    }

    public void setEofficeReciptCmpNo(Integer eofficeReciptCmpNo) {
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

    public String getEofficeCurrenetlyWith() {
        return eofficeCurrenetlyWith;
    }

    public void setEofficeCurrenetlyWith(String eofficeCurrenetlyWith) {
        this.eofficeCurrenetlyWith = eofficeCurrenetlyWith;
    }

    public Integer getEofficeSinceWhen() {
        return eofficeSinceWhen;
    }

    public void setEofficeSinceWhen(Integer eofficeSinceWhen) {
        this.eofficeSinceWhen = eofficeSinceWhen;
    }

    public String getEofficeClosingRemarks() {
        return eofficeClosingRemarks;
    }

    public void setEofficeClosingRemarks(String eofficeClosingRemarks) {
        this.eofficeClosingRemarks = eofficeClosingRemarks;
    }

    public String getEofficeFilenumber() {
        return eofficeFilenumber;
    }

    public void setEofficeFilenumber(String eofficeFilenumber) {
        this.eofficeFilenumber = eofficeFilenumber;
    }

    public String getEofficeFileCmpNo() {
        return eofficeFileCmpNo;
    }

    public void setEofficeFileCmpNo(String eofficeFileCmpNo) {
        this.eofficeFileCmpNo = eofficeFileCmpNo;
    }

    public String getEofficeReciptUpdatedOn() {
        return eofficeReciptUpdatedOn;
    }

    public void setEofficeReciptUpdatedOn(String eofficeReciptUpdatedOn) {
        this.eofficeReciptUpdatedOn = eofficeReciptUpdatedOn;
    }

    public Integer getEofficeDepCode() {
        return eofficeDepCode;
    }

    public void setEofficeDepCode(Integer eofficeDepCode) {
        this.eofficeDepCode = eofficeDepCode;
    }
}
