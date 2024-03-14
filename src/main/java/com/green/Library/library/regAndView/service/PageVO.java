package com.green.Library.library.regAndView.service;

public class PageVO {
    private int nowPage; //현재 선택된 페이지 번호
    private int totalDataCnt; //전체 게시물 갯수
    private int displayDataCnt; //한 페이지에 보여지는 데이터 수 무조건 올림 처리
    private int displayPageCnt; //한 페이지에 보여지는 페이지 수
    private int beginPage; // 화면에 보이는 첫 번째 페이지 번호 다음 버튼누르면 맨 처음 가는 페이지
    private int endPage; // 화면에 보이는 마지막 페이지 번호
    private boolean prev; // 이전 버튼 유무
    private boolean next; // 다음 버튼 유무

    int totalPageCnt; //전체 페이지 번호
    public PageVO(){
        //생성자
        nowPage = 1;
        displayDataCnt = 20; //화면에 보여질 게시물 갯수 초기화
        displayPageCnt = 5; //화면에 보여질 페이지 번호 초기화
    }

    public void setPageInfo(){
        //화면에 보이는 마지막 페이지 번호 세팅

        //화면에 보이는 마지막 페이지 번호 = 올림처리 (현재 선택된 페이지 / 한 페이지에 보여지는 페이지수) * 한 페이지에 보이는 페이지 수
        endPage = (int)Math.ceil(nowPage/(double)displayPageCnt) * displayPageCnt;

        //화면에 보이는 첫 번째 페이지 번호 세팅 (화면에 보이는 페이지 마지막 번호 - 한 페이지에 보여지는 페이지 수)
        beginPage = endPage - displayPageCnt + 1;

        //전체 페이지 수
        //올림처리 (전체 데이터수 / 화면에 보여지는 데이터 수)
        totalPageCnt = (int) Math.ceil(totalDataCnt/(double)displayDataCnt);

        //다음 버튼 이전 버튼 유무 확인
        //만약 (화면에 보이는 마지막 페이지 번호) < (전체 페이지 번호)
        if(endPage < totalPageCnt){
            next = true;
            //다음 버튼 활성화
        }else{
            next = false;
            //다음 버튼 비 활성화 (마지막 페이지로 이동했으므로)
            //마지막 페이지 까지 이동한것 이므로 서로 맞춰줌
            endPage = totalPageCnt;
        }

        //이전 버튼 화면에 보이는 첫번째 페이지 번호가 1이면 이전버튼이 안보여야함
        //1이 아니면 보여야함
        if(beginPage == 1 ){
            prev = false;
        }else{
            prev = true;
        }


    }

    public void setNowPage(int nowPage){
        this.nowPage = nowPage;
    }
    public int getNowPage(){
        return nowPage;
    }
    public int getEndPage(){
        return endPage ;
    }
    public int getBeginPage(){
        return beginPage;
    }
    public int getDisplayDataCnt(){
        return displayDataCnt;
    }
    public void setEndPage(int a){
        this.endPage = a;
    }

    public void setTotalDataCnt(int a){
        this.totalDataCnt = a;
    }
    public int getTotalPageCnt(){
        return totalPageCnt;
    }
    public boolean getPrev(){
        return prev;
    }
    public boolean getNext(){
        return next;
    }
}
