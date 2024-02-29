package com.green.Library.util;

import com.green.Library.web.img.vo.ImgVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardUploadUtil {
    //파일 확장자를 문자열로 리턴
    public static String getBoardExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //uuid를 통해서 랜덤한 파일명 생성하기
    public static String getBoardUUID(){
        return UUID.randomUUID().toString();
    }

    //이미지 올리기 리턴값은 imgVO
    public static ImgVO uploadFile(MultipartFile upLoadFile){
        //파일 첨부 기능
        //(첨부한 파일이 존재할 때 실행함)
        ImgVO imgVO = null;

        if(!upLoadFile.isEmpty()){
            imgVO = new ImgVO();

            //확장자
            String extension = getBoardExtension(upLoadFile.getOriginalFilename());

            //무작위로 만든 이름에 확장자 붙이기
            String boardFileName = getBoardUUID() + extension;

            // 파일 첨부
            try {
                File mainFile = new File(ConstantVariable.UPLOAD_PATH + boardFileName);
                imgVO.setAttachedFileName(boardFileName);
                imgVO.setOriginFileName(upLoadFile.getOriginalFilename());


                upLoadFile.transferTo(mainFile);
                imgVO.setIsMain("Y");
            } catch (Exception e) {
                System.out.println("예외발생");
                e.printStackTrace();
            }
        }
            return imgVO;
    }

    // 다중 이미지 첨부 파일 기능
    public static List<ImgVO> subImgUploadFile(MultipartFile[] uploadFiles){
        List<ImgVO> imgList = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles){
            ImgVO img = uploadFile(uploadFile);
            if (img != null){
                img.setIsMain("N");
                imgList.add(img);
            }
        }
        return imgList;
    }
}
