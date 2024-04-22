package com.green.Library.web.findBook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SearchDateVO extends PageVO{
    private String searchDate;
    private String bookCateName;
    private String bookMidCateName;
    private String bookBorrowAvailable;
}
