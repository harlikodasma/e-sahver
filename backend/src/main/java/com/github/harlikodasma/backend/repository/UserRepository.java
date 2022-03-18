package com.github.harlikodasma.backend.repository;

import com.github.harlikodasma.backend.bean.UserQueryReturn;
import com.github.harlikodasma.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "SELECT user FROM User user WHERE user.id = ?1")
    User findByID(Long id);

    @Query(value = "SELECT new com.github.harlikodasma.backend.bean.UserQueryReturn(user.id, user.email, COUNT(DISTINCT item.id), COUNT(DISTINCT itemWithImage.id), COUNT(DISTINCT storage.id), COUNT(DISTINCT lowerStorage.id)) FROM User user LEFT OUTER JOIN Item item ON user.id = item.addedByID LEFT OUTER JOIN Item itemWithImage ON user.id = itemWithImage.addedByID AND itemWithImage.pictureID IS NOT NULL LEFT OUTER JOIN Storage storage ON user.id = storage.creatorID LEFT OUTER JOIN Storage lowerStorage ON user.id = lowerStorage.creatorID AND lowerStorage.parentID IS NOT NULL GROUP BY user.id, user.email")
    List<UserQueryReturn> userStats();
}
