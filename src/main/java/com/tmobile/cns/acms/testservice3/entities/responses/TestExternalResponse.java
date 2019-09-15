package com.tmobile.cns.acms.testservice3.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestExternalResponse {
    private String responseStatus;
    private boolean isValidated;
}
