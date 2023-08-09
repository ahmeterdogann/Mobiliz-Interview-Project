package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.GroupToGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupToGroupRepository extends JpaRepository<GroupToGroup, Long> {
    @Query("SELECT new Group(g.id, g.name, g.company, g.isRoot) FROM GroupToGroup g2g, Group g WHERE g2g.childGroup.id = :childId and g2g.parentGroup.id = g.id")
    Group findParent(@Param("childId") Long childId);
    @Query("SELECT new Group(g.id, g.name, g.company, g.isRoot) FROM GroupToGroup g2g, Group g WHERE g2g.parentGroup.id = :parentId and g2g.childGroup.id = g.id")
    List<Group> findAllByParentGroup(@Param("parentId") Long parentId);
}
