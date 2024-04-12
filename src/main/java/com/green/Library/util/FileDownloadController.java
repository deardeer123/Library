package com.green.Library.util;

import com.green.Library.web.board.vo.UploadVO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileDownloadController {

//    //파일 다운로드
//    @ResponseBody
//    @PostMapping("/download")
//    public ResponseEntity<Resource> downloadFile(UploadVO uploadVO) throws Exception {
//
//        System.out.println(uploadVO);
//        //파일 경로
//        String path = ConstantVariable.UPLOAD_PATH;
//        //저장된 파일명
//        String uploadFileName = uploadVO.getAttachedFileName();
//        //저장된 파일의 원래 이름
//        String uploadFileOriginName = uploadVO.getOriginFileName();
//
//        //UrlResource는 Resource 인터페으스이 구현체로 new UrlResource("file:" + "파일이 저장된 경로")로 사용
//        UrlResource resource = new UrlResource("file:" + path + uploadFileName);
//
//        //다운로드 할때의 파일명 지정
//        String encodeFileName = UriUtils.encode(uploadFileOriginName, StandardCharsets.UTF_8);
//
//        //파일 다운로드 대화상자가 뜨도록 헤더를 성정해주는것
//        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
//        String contentDisposition = "attachment; filename=\"" + uploadFileName + "\"";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//
//    }

}
