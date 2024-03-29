package org.choongang.board.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestBoard {
    private String mode = "write";
    private Long seq;   // 게시글 번호
    private String bid; // 게시판 ID
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String poster;  // 글 작성자

    private String guestPw; // 비회원 비밀번호

    private boolean notice; // 공지사항 여부

    @NotBlank
    private String subject; // 글 제목
    @NotBlank
    private String content; // 글 내용

    private List<FileInfo> editorFiles; // 에디터 파일 목록
    private List<FileInfo> attachFiles; // 첨부 파일 목록
}
