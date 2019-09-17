package com.tony.test.testservice3.entities;

import com.tony.test.testservice3.entities.responses.BaseError;
import com.tony.test.testservice3.entities.responses.TestExternalResponse;

public class SuccessResponse extends BaseResponse<TestExternalResponse> {
    public SuccessResponse(TestExternalResponse success, BaseError error) {
        super(success, error);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}