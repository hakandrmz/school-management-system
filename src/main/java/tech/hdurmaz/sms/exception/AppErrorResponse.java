package tech.hdurmaz.sms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
