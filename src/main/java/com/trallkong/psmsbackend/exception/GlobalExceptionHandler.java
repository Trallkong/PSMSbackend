// GlobalExceptionHandler.java
package com.trallkong.psmsbackend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getErrorCode());
        response.put("message", e.getMessage());

        // 添加详细信息
        Map<String, Object> details = new HashMap<>();
        details.put("constraint", "fk_bill_item");
        details.put("referencedTable", "bill");
        details.put("referencingTable", "charge_item");
        response.put("details", details);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "DATA_INTEGRITY_VIOLATION");
        response.put("message", "数据完整性约束违反");
        response.put("details", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
