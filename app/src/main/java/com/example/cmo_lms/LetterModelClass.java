package com.example.cmo_lms;

import android.os.Parcel;
import android.os.Parcelable;


public class LetterModelClass implements Parcelable {
    private String ref_No;
    private String representative_Type;
    private String constituencyName_of_Representatives;
    private String department_Line_Department_District;
    private String subject;
    private String letter_description;
    private String cmo_remark;
    private String pending_with_Closed_by;
    private String eOffice_Computers_No;

    private String eOffice_Receipt_Date;
    private String eOffice_Status;

    // Parcelable implementation
    protected LetterModelClass(Parcel in) {
        ref_No = in.readString();
        representative_Type = in.readString();
        constituencyName_of_Representatives = in.readString();
        department_Line_Department_District = in.readString();
        subject = in.readString();
        letter_description = in.readString();
        cmo_remark = in.readString();
        pending_with_Closed_by = in.readString();
        eOffice_Computers_No = in.readString();
        eOffice_Receipt_Date = in.readString();
        eOffice_Status = in.readString();
    }

    public static final Creator<LetterModelClass> CREATOR = new Creator<LetterModelClass>() {
        @Override
        public LetterModelClass createFromParcel(Parcel in) {
            return new LetterModelClass(in);
        }

        @Override
        public LetterModelClass[] newArray(int size) {
            return new LetterModelClass[size];
        }
    };

    public LetterModelClass(String key, String value) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ref_No);
        dest.writeString(representative_Type);
        dest.writeString(constituencyName_of_Representatives);
        dest.writeString(department_Line_Department_District);
        dest.writeString(subject);
        dest.writeString(letter_description);
        dest.writeString(cmo_remark);
        dest.writeString(pending_with_Closed_by);
        dest.writeString(eOffice_Computers_No);
        dest.writeString(eOffice_Receipt_Date);
        dest.writeString(eOffice_Status);
    }

    public String getRef_No() {
        return ref_No;
    }

    public void setRef_No(String ref_No) {
        this.ref_No = ref_No;
    }

    public String getRepresentative_Type() {
        return representative_Type;
    }

    public void setRepresentative_Type(String representative_Type) {
        this.representative_Type = representative_Type;
    }

    public String getConstituencyName_of_Representatives() {
        return constituencyName_of_Representatives;
    }

    public void setConstituencyName_of_Representatives(String constituencyName_of_Representatives) {
        this.constituencyName_of_Representatives = constituencyName_of_Representatives;
    }

    public String getDepartment_Line_Department_District() {
        return department_Line_Department_District;
    }

    public void setDepartment_Line_Department_District(String department_Line_Department_District) {
        this.department_Line_Department_District = department_Line_Department_District;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLetter_description() {
        return letter_description;
    }

    public void setLetter_description(String letter_description) {
        this.letter_description = letter_description;
    }

    public String getCmo_remark() {
        return cmo_remark;
    }

    public void setCmo_remark(String cmo_remark) {
        this.cmo_remark = cmo_remark;
    }

    public String getPending_with_Closed_by() {
        return pending_with_Closed_by;
    }

    public void setPending_with_Closed_by(String pending_with_Closed_by) {
        this.pending_with_Closed_by = pending_with_Closed_by;
    }

    public String geteOffice_Computers_No() {
        return eOffice_Computers_No;
    }

    public void seteOffice_Computers_No(String eOffice_Computers_No) {
        this.eOffice_Computers_No = eOffice_Computers_No;
    }

    public String geteOffice_Receipt_Date() {
        return eOffice_Receipt_Date;
    }

    public void seteOffice_Receipt_Date(String eOffice_Receipt_Date) {
        this.eOffice_Receipt_Date = eOffice_Receipt_Date;
    }

    public String geteOffice_Status() {
        return eOffice_Status;
    }

    public void seteOffice_Status(String eOffice_Status) {
        this.eOffice_Status = eOffice_Status;
    }

}
