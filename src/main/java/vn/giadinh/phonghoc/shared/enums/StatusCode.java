package vn.giadinh.phonghoc.shared.enums;

public enum StatusCode {
    SUCCESS("Success"),
    FAILURE("Failure");

    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
