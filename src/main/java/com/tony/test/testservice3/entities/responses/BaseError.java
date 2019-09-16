package com.tony.test.testservice3.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BaseError {
    private String code;
    private String reason;
    private String explanation;
}