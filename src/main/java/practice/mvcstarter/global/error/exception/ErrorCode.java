package practice.mvcstarter.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    /* Common */
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    /* Time */
    INVALID_ACCESS_TIME(500, "C007", "Invalid Access Time"),

    /* Member */
//    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
//    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    /* File */
    FILE_IS_NOT_BASE64(400, "C008", "File is not base64"),
    FILE_HAS_EXPIRED(400, "C009", "File has expired"),
    FAILED_TO_READ_FILE(400, "C010", "Failed to read file"),
    FAILED_TO_STORE_FILE(400, "C011", "Failed to store file"),
    FAILED_TO_DELETE_FILE(400, "C012", "Failed to delete file"),
    ;

    private final String code;
    private final String message;
    private final int    status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
