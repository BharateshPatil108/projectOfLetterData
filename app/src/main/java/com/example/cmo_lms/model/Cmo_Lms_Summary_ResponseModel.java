package com.example.cmo_lms.model;

public class Cmo_Lms_Summary_ResponseModel {
    private String name;
    private int Count;
    private int PendingCount;
    private int ClosedCount;
    private int AcceptedClosedCount;
    private int RejectedClosedCount;

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setPendingCount(int pendingCount) {
        PendingCount = pendingCount;
    }

    public void setClosedCount(int closedCount) {
        ClosedCount = closedCount;
    }

    public void setAcceptedClosedCount(int acceptedClosedCount) {
        AcceptedClosedCount = acceptedClosedCount;
    }

    public void setRejectedClosedCount(int rejectedClosedCount) {
        RejectedClosedCount = rejectedClosedCount;
    }

    public int getCount() {
        return Count;
    }

    public int getPendingCount() {
        return PendingCount;
    }

    public int getClosedCount() {
        return ClosedCount;
    }

    public int getAcceptedClosedCount() {
        return AcceptedClosedCount;
    }

    public int getRejectedClosedCount() {
        return RejectedClosedCount;
    }

    public String getName() {
        return name;
    }
}
