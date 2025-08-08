package vn.giadinh.phonghoc.shared.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS("Success"),
    FAILURE("Failure");
    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

}
