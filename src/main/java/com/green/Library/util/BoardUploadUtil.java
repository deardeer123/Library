package com.green.Library.util;

import com.green.Library.web.board.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public static UploadVO uploadFile(MultipartFile upLoadFile){
        //파일 첨부 기능
        //(첨부한 파일이 존재할 때 실행함)
        UploadVO uploadVO = null;

        if(!upLoadFile.isEmpty()){
            uploadVO = new UploadVO();

            //확장자
            String extension = getBoardExtension(upLoadFile.getOriginalFilename());

            //무작위로 만든 이름에 확장자 붙이기
            String boardFileName = getBoardUUID() + extension;

            // 파일 첨부
            try {
                File mainFile = new File(ConstantVariable.UPLOAD_PATH + boardFileName);
                uploadVO.setAttachedFileName(boardFileName);
                uploadVO.setOriginFileName(upLoadFile.getOriginalFilename());

                

                upLoadFile.transferTo(mainFile);
                uploadVO.setIsMain("Y");
            } catch (Exception e) {
                System.out.println("예외발생");
                e.printStackTrace();
            }
        }
            return uploadVO;
    }

    // 다중 이미지 첨부 파일 기능
    public static List<UploadVO> subImgUploadFile(MultipartFile[] uploadFiles){
        List<UploadVO> fileList = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles){
            UploadVO file = uploadFile(uploadFile);
            if (file != null){
                file.setIsMain("N");
                fileList.add(file);
            }
        }
        return fileList;
    }


//    public static Optional<UploadVO> uploadFile1(MultipartFile upLoadFile){
//        //파일 첨부 기능
//        //(첨부한 파일이 존재할 때 실행함)
//        Optional<UploadVO> uploadVO = null;
//
//        if(!upLoadFile.isEmpty()){
//            uploadVO = Optional.empty();
//
//            //확장자
//            String extension = getBoardExtension(upLoadFile.getOriginalFilename());
//
//            //무작위로 만든 이름에 확장자 붙이기
//            String boardFileName = getBoardUUID() + extension;
//
//            // 파일 첨부
//            try {
//                File mainFile = new File(ConstantVariable.UPLOAD_PATH + boardFileName);
//                uploadVO.
//                uploadVO.setAttachedFileName(boardFileName);
//                uploadVO.setOriginFileName(upLoadFile.getOriginalFilename());
//
//
//                upLoadFile.transferTo(mainFile);
//                uploadVO.setIsMain("Y");
//            } catch (Exception e) {
//                System.out.println("예외발생");
//                e.printStackTrace();
//            }
//        }
//        return uploadVO;
//    }
}
