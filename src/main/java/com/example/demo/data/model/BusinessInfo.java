package com.example.demo.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class BusinessInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String email;
    private String logoUrl;
    private String phone;
    private String address;
    private String primaryColor;
    private String secondaryColor;
    private String websiteUrl;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;
}
