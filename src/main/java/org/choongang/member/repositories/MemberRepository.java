package org.choongang.member.repositories;


import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByUserId(String userId);

    default boolean existByEmail(String email) {
        QMember member = QMember.member;
        return exists(member.email.eq(email));
    }

    default boolean existByUserId(String userId) {
        QMember member = QMember.member;
        return exists(member.userId.eq(userId));
    }
}
