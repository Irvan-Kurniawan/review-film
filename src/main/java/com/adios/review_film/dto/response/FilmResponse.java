package com.enigma.tokonyadia_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponse {
    private String id;
    private String name;
    private boolean isMember;
    private String userCredentialId;
}
