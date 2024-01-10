package org.choongang.admin.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestBoardConfig {
    private String mode = "add";

    private String gid = UUID.randomUUID().toString();  // 파일 추가할 때 필수로 넣어주어야 함

    @NotBlank
    private String bid;     // 게시판 아이디 -> 엔티티의 기본키

    @NotBlank
    private String bName;   // 게시판 이름

    // 나머지는 기본값
    private boolean active; // 사용 여부

    private int rowsPerPage = 20;   // 1페이지 게시글 수

    private int pageCountPc = 10;   // PC 페이지 구간 갯수

    private int PageCountMobile = 5;    // Mobile 페이지 구간 갯수

    private boolean useReply;   // 답글 사용 여부

    private boolean useComment; // 댓글 사용 여부

    private boolean useEditor;  // 에디터 사용 여부

    private boolean useUploadImage; // 이미지 첨부 사용 여부

    private boolean useUploadFile;  // 파일 첨부 사용 여부

    private String locationAfterWriting = "list";    // 글 작성 후 이동 위치

    private String skin = "default";    // 스킨

    private String category;    // 게시판 분류

    private String listAccessType = "All";  // 권한 설정 - 글목록

    private String viewAccessType = "ALL";  // 권한 설정 - 글보기

    private String writeAccessType = "ALL"; // 권한 설정 - 글쓰기

    private String replyAccessType = "ALL"; // 권한 설정 - 답글

    private String commentAccessType = "ALL";   // 권한 설정 - 댓글

    private String htmlTop; // 게시판 상단 HTML
    private String htmlBottom;  // 게시판 하단 HTML
}
