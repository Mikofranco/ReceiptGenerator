package com.example.demo.service.businessInfo;

import com.example.demo.data.model.BusinessInfo;
import com.example.demo.data.model.User;
import com.example.demo.data.repo.BusinessInfoRepo;
import com.example.demo.dto.requests.BusinessInfoRequest;
import com.example.demo.dto.response.BusinessInfoResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.Auth.UserService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessInfoImpl implements BusinessInfoService {
    private final BusinessInfoRepo businessInfoRepo;
    private final UserService userService;

    @Override
    public BusinessInfoResponse createBusinessInfo(BusinessInfoRequest request) {
        User foundUser = userService.findUserById(request.getUserId());

        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setCreatedBy(foundUser);
        businessInfo.setEmail(request.getEmail());
        businessInfo.setPhone(request.getPhone());
        businessInfo.setAddress(request.getAddress());
        businessInfo.setPrimaryColor(request.getPrimaryColor());
        businessInfo.setSecondaryColor(request.getSecondaryColor());
        businessInfo.setWebsiteUrl(request.getWebsiteUrl());
        BusinessInfo savedBusinessInfo =businessInfoRepo.save(businessInfo);

        return mapBusinessInfoResponse(savedBusinessInfo);
    }

    @Nonnull
    public static BusinessInfoResponse mapBusinessInfoResponse(BusinessInfo savedBusinessInfo) {
        BusinessInfoResponse response = new BusinessInfoResponse();
        response.setId(savedBusinessInfo.getId());
        response.setPhone(savedBusinessInfo.getPhone());
        response.setAddress(savedBusinessInfo.getAddress());
        response.setPrimaryColor(savedBusinessInfo.getPrimaryColor());
        response.setSecondaryColor(savedBusinessInfo.getSecondaryColor());
        response.setWebsiteUrl(savedBusinessInfo.getWebsiteUrl());
        response.setEmail(savedBusinessInfo.getEmail());
        response.setLogoUrl(savedBusinessInfo.getLogoUrl());
        return response;
    }

    @Override
    public BusinessInfoResponse updateBusinessInfo(BusinessInfoRequest request) {
        return null;
    }

    @Override
    public BusinessInfoResponse findBusinessInfoById(String businessInfoId) {
        BusinessInfo fetchedData= businessInfoRepo.findById(businessInfoId)
                .orElseThrow(()-> new RuntimeException("Business info with "+businessInfoId+" does not exist"));
        return mapBusinessInfoResponse(fetchedData);
    }
}
