package com.green.Library.web.board.vo;

import lombok.*;

//@Getter
//@Setter
//@ToString
//생성자를 구현하는 어노테이션
//@NoArgsConstructor //매개변수가 없는 기본 생성자 생성
//@AllArgsConstructor // 멤버변수 모두를 매개변수로 받는 생성자 생성
// @RequiredArgsConstructor //필요한 멤버변수를 매개변수로 받는 생성자 생성
//기본 생성자, setter getter toString

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadVO {
    private int AttachedCode;
    private String OriginFileName;
    private String AttachedFileName;
    private String isMain;
    private int boardNum;
}
