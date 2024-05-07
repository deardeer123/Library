package com.green.Library.library.libraryhome.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoSearchVO extends MemoPageVO{
    private String searchType;
    private String searchValue;
}
