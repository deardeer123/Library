package com.green.Library.libraryBook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookRecommendationVO {
    private long id;
    private String bookCode;
    private String userType;
}
