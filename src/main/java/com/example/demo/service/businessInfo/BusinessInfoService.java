package com.example.demo.service.businessInfo;

import com.example.demo.dto.requests.BusinessInfoRequest;
import com.example.demo.dto.response.BusinessInfoResponse;

public interface BusinessInfoService {
    BusinessInfoResponse createBusinessInfo(BusinessInfoRequest request);
    BusinessInfoResponse updateBusinessInfo(BusinessInfoRequest request);
    BusinessInfoResponse findBusinessInfoById(String businessInfoId);

}
