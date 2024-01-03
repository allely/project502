package org.choongang.member.services;

import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class MemberNotFountException extends CommonException {

    public MemberNotFountException() {
        super("등록된 회원이 아닙니다.", HttpStatus.NOT_FOUND);
    }
}
