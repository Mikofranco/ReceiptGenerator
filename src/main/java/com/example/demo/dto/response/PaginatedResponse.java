package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class PaginatedResponse<T> {
    private List<T> data;
    private int currentPage;
    private int totalPage;
    private long totalCount;
}
