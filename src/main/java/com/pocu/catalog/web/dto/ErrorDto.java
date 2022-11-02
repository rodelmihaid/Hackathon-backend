package com.pocu.catalog.web.dto;

public class ErrorDto {

    private String errorCode;
    private String message;
    private Integer status;

    public ErrorDto() {
    }

    public ErrorDto(ErrorDtoBuilder errorDtoBuilder) {
        this.errorCode = errorDtoBuilder.errorCode;
        this.message = errorDtoBuilder.message;
        this.status = errorDtoBuilder.status;
    }

    public static ErrorDtoBuilder builder() {
        return new ErrorDtoBuilder();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static class ErrorDtoBuilder {
        private String errorCode;
        private String message;
        private Integer status;

        private ErrorDtoBuilder() {

        }

        public ErrorDtoBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDtoBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorDtoBuilder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public ErrorDto build() {
            return new ErrorDto(this);
        }
    }
}
