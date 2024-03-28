package org.common.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.enums.StatusResponse;

@RequiredArgsConstructor
@Data
public class ApiResponse {
    private StatusResponse status;
    private String message;
    private Object data;

    public ApiResponse(StatusResponse status) {
        this.status = status;
    }

    public ApiResponse(StatusResponse status, Object data) {
        this.status = status;
        this.data = data;
    }
}
