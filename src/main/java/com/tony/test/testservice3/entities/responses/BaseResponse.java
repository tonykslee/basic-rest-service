package com.tony.test.testservice3.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<Body> {
    private Body body;
    private BaseError baseError;
}