package com.example.demo.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DeleteCustomerResponse {
    private String id;
    private String message;
}
