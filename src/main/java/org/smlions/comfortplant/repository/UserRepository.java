package org.smlions.comfortplant.repository;

import org.smlions.comfortplant.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPassword(String password);

    //중복 확인을 위해 이메일을 이용하여 찾음
    boolean existsByEmail(String email);
}
