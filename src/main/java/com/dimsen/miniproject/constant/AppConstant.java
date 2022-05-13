package com.dimsen.miniproject.constant;
public class AppConstant {

    private AppConstant() {}

    public static final String DEFAULT_SYSTEM = "SYSTEM";

    public enum ResponseCode {

        SUCCESS("SUCCESS", "Success"),
        DATA_NOT_FOUND("DATA_NOT_FOUND", "Data not found"),
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
        UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown Error Happened");

        private final String code;
        private final String message;

        private ResponseCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

    }

    public enum UserRole {
        COMPANY("company"),
        APPLICANT("applicant");

        private final String role;

        UserRole(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    public enum AccountStatus {
        OPEN("open"),
        EXPIRED("expired"),
        LOCKED("locked");

        private final String status;

        AccountStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
