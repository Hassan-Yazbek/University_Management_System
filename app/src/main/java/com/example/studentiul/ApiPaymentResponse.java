package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiPaymentResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("payment")
    private List<Payment> payment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public List<Payment> getPayment() {
        return payment;
    }
    // Getters and setters

    public static class Payment {
        @SerializedName("students_id")
        private String students_id;
        @SerializedName("Method")
        private String Method;
        @SerializedName("annual_amount")
        private double annualAmount;
        @SerializedName("PaymentDate")
        private String PaymentDate;
        @SerializedName("YearID")
        private String YearID;
        @SerializedName("paid_amount")
        private String paidAmount;
        @SerializedName("RemainingAmount")
        private double RemainingAmount;
        @SerializedName("annual_remaining")
        private String annualRemaining;


        public void setYearID(String yearID) {
            YearID = yearID;
        }

        public String getYearID() {
            return YearID;
        }

        public void setAnnualAmount(double annualAmount) {
            this.annualAmount = annualAmount;
        }

        public double getAnnualAmount() {
            return annualAmount;
        }

        public void setMethod(String method) {
            Method = method;
        }

        public String getMethod() {
            return Method;
        }

        public void setPaymentDate(String paymentDate) {
            PaymentDate = paymentDate;
        }

        public double getRemainingAmount() {
            return RemainingAmount;
        }

        public void setRemainingAmount(double remainingAmount) {
            RemainingAmount = remainingAmount;
        }

        public String getPaymentDate() {
            return PaymentDate;
        }

        public void setStudents_id(String students_id) {
            this.students_id = students_id;
        }

        public String getStudents_id() {
            return students_id;
        }

        public void setAnnualRemaining(String annualRemaining) {
            this.annualRemaining = annualRemaining;
        }

        public String getAnnualRemaining() {
            return annualRemaining;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getPaidAmount() {
            return paidAmount;
        }
    }
}
