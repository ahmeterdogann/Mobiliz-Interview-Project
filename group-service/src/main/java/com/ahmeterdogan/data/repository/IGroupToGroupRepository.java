package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.GroupToGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IGroupToGroupRepository extends JpaRepository<GroupToGroup, Long> {
    @Query("SELECT new Group(g.id, g.name, g.company, g.root) FROM GroupToGroup g2g, Group g WHERE g2g.childGroup.id = :childId and g2g.parentGroup.id = g.id")
    Group findParent(@Param("childId") Long childId);
    @Query("SELECT new Group(g.id, g.name, g.company, g.root) FROM GroupToGroup g2g, Group g WHERE g2g.parentGroup.id = :parentId and g2g.childGroup.id = g.id")
    Set<Group> findAllByParentGroup(@Param("parentId") Long parentId);

    Optional<GroupToGroup> findByChildGroup(Group childGroup);

    int deleteByParentGroupAndChildGroup(Group parentGroup, Group childGroup);

    void deleteByParentGroup_IdOrChildGroup_Id(long groupId, long groupId2);
}
