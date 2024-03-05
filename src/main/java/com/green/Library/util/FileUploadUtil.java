package com.green.Library.util;

import com.green.Library.web.board.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUploadUtil {
    //파일의 확장자를 문자열로 리턴하는 메소드
    public static String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //uuid를 통한 파일명 생성
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    //단일 파일 업로드 메소드
    public static UploadVO fileUpload(MultipartFile fileUpload){
        UploadVO uploadVO = null;

        if(!fileUpload.isEmpty()){
            uploadVO = new UploadVO();

            //확장자 추출
            String extension = getExtension(fileUpload.getOriginalFilename());

            //중복되지 않는 파일명 생성
            String fileName = getUUID() + extension;

            try{
                File file1 = new File(ConstantVariable.UPLOAD_PATH + fileName);
                fileUpload.transferTo(file1);

                uploadVO.setUploadAttachedFileName(fileName);
                uploadVO.setUploadOriginFileName(fileUpload.getOriginalFilename());
            }catch(Exception e){
                System.out.println("파일 첨부 중 예외 발생~");
                e.printStackTrace();
            }
        }
        return uploadVO;
    }
    //다중 첨부 메소드
    public static List<UploadVO> multiFileUpload(MultipartFile[] uploadFiles){
        List<UploadVO> uploadList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){
            UploadVO upload = fileUpload(uploadFile);
        }
        return uploadList;
    }




}
