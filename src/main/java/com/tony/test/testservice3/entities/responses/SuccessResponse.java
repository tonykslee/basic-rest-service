package com.tony.test.testservice3.entities.responses;

public class SuccessResponse extends BaseResponse<TestResponse> {
    public SuccessResponse(TestResponse success, BaseError error) {
        super(success, error);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}