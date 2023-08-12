package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.UserGroupAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface IUserGroupAuthRepository extends JpaRepository<UserGroupAuthorization, Long> {
    @Query("SELECT new Group (g.id, g.name, g.company, g.root) FROM UserGroupAuthorization ug, Group g WHERE ug.user.id = :userId and ug.group.id = g.id")
    Set<Group> findAllGroupsByUser(@Param("userId") long userId);

    void deleteByGroup_Id(long groupId);
}
