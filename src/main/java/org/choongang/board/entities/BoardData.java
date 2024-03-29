package org.choongang.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.commons.entities.BaseMember;
import org.choongang.member.entities.Member;

import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_boardData_basic", columnList = "notice DESC, createdAt DESC")
})
public class BoardData extends Base {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardSeq")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private Member member;

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    @Column(length = 40, nullable = false)
    private String poster;  // 작성자
    private String guestPw; // 비회원 비밀번호

    private boolean notice; // 공지글 여부 - true : 공지글

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    private int viewCount;  // 조회수

    @Column(length = 20)
    private String ip;  // IP 주소

    private String ua;  // User-Agent : 브라우저 정보

    private int num1;   // 추가 필드 : 정수
    private int num2;   // 추가 필드 : 정수
    private int num3;   // 추가 필드 : 정수

    @Column(length = 100)
    private String text1;   // 추가 필드 : 한줄 텍스트
    @Column(length = 100)
    private String text2;   // 추가 필드 : 한줄 텍스트
    @Column(length = 100)
    private String text3;   // 추가 필드 : 한줄 텍스트

    @Lob
    private String longText1;   // 추가 필드 : 여러줄 텍스트
    @Lob
    private String longText2;   // 추가 필드 : 여러줄 텍스트
    @Lob
    private String longText3;   // 추가 필드 : 여러줄 텍스트

}
