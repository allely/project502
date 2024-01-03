package org.choongang.member.services;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // 특정 사용자만 사용할 수 있도록 권한 확인(인가)
        return authorities;
    }

    @Override
    public String getPassword() {   //
        return password;
    }

    @Override
    public String getUsername() {
        return StringUtils.hasText(email)? email : userId;  // email 있으면 email로, 없으면 id로
    }

    @Override
    public boolean isAccountNonExpired() {  // 계정 만료 여부 확인 -> 접근 O/X
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   //  계정 잠김 확인
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  // 주기적으로 비밀번호 재설정하도록
        return true;
    }

    @Override
    public boolean isEnabled() {    //
        return true;
    }
}
