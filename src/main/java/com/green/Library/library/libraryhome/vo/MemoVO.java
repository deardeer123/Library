package com.green.Library.library.libraryhome.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoVO {
    private int id;
    private String memoTitle;
    private String memoContent;
    private String memoWriter;
    private String date;
}
