package com.tony.test.testservice3.entities;

import com.tony.test.testservice3.entities.responses.BaseError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<Body> {
    private Body body;
    private BaseError baseError;
}
