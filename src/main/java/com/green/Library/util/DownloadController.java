package com.green.Library.util;

import com.green.Library.web.board.vo.UploadVO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;


@Controller
public class DownloadController {
    //파일 다운로드
    @RequestMapping("/download")
    public ResponseEntity<Resource> downloadFile(UploadVO uploadVO) throws Exception {

        System.out.println(uploadVO);
        //파일 경로
        String path = ConstantVariable.UPLOAD_PATH;
        //저장된 파일명
        String uploadFileName = uploadVO.getAttachedFileName();
        //저장된 파일의 원래 이름
        String uploadFileOriginName = uploadVO.getOriginFileName();

        System.out.println(uploadFileName);
        System.out.println(uploadFileOriginName);

        //UrlResource는 Resource 인터페으스이 구현체로 new UrlResource("file:" + "파일이 저장된 경로")로 사용
        UrlResource resource = new UrlResource("file:" + path + uploadFileName);

        System.out.println(resource.getFilename());

        //다운로드 할때의 파일명 지정
        String encodeFileName = UriUtils.encode(uploadFileOriginName, StandardCharsets.UTF_8);

        //파일 다운로드 대화상자가 뜨도록 헤더를 성정해주는것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);

    }

}
