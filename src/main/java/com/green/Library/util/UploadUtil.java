package com.green.Library.util;

import com.green.Library.libraryBook.vo.LibraryBookInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadUtil {
    //파일 확장자를 문자열로 리턴
    public static String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //uuid를 통해서 랜덤한 파일명 생성하기
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    //이미지 올리기 리턴값은 libraryBookInfoVO
    public static LibraryBookInfoVO uploadFile(MultipartFile upLoadFile){
        LibraryBookInfoVO libraryBookInfoVO = new LibraryBookInfoVO();

        try {
        //확장자
        String extension = getExtension(upLoadFile.getOriginalFilename());

        //무작위로 만든 이름에 확장자 붙이기
        String fileName = getUUID() + extension;

        libraryBookInfoVO.setBookInfoOriginFileName(upLoadFile.getOriginalFilename());
        libraryBookInfoVO.setBookInfoAttachedFileName(fileName);

        File file = new File(ConstantVariable.UPLOAD_PATH + fileName);
        upLoadFile.transferTo(file);
        } catch (Exception e) {
            System.out.println("예외발생");
            e.printStackTrace();
        }

        return libraryBookInfoVO;
    }

}
