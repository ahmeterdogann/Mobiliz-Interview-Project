package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.GroupToGroup;
import com.ahmeterdogan.data.entity.UserGroupAuthorization;
import com.ahmeterdogan.data.repository.IGroupRepository;
import com.ahmeterdogan.data.repository.IGroupToGroupRepository;
import com.ahmeterdogan.data.repository.IUserGroupAuthRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
public class GroupServiceHelper {
    private final IGroupToGroupRepository groupToGroupRepository;
    private final IGroupRepository groupRepository;
    private final IUserGroupAuthRepository userGroupAuthRepository;

    public GroupServiceHelper(IGroupToGroupRepository groupToGroupRepository, IGroupRepository groupRepository, IUserGroupAuthRepository userGroupAuthRepository) {
        this.groupToGroupRepository = groupToGroupRepository;
        this.groupRepository = groupRepository;
        this.userGroupAuthRepository = userGroupAuthRepository;
    }


    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void saveUserGroupAuth(UserGroupAuthorization userGroupAuthorization) {
        userGroupAuthRepository.save(userGroupAuthorization);
    }

    public Optional<Group> getGroupByIdAndCompanyId(long groupId, long companyId) {
        return groupRepository.findByIdAndCompany_Id(groupId, companyId);
    }

    public void saveGroupToGroup(Group parentGroup, Group childGroup) {
        GroupToGroup groupToGroup = GroupToGroup.builder()
                .parentGroup(parentGroup)
                .childGroup(childGroup)
                .build();

        groupToGroupRepository.save(groupToGroup);
    }

    public Set<Group> getChildren(Group group) {
        return groupToGroupRepository.findAllByParentGroup(group.getId());
    }

    public Set<Group> getUserGroupAuth(long userId) {
        return userGroupAuthRepository.findAllGroupsByUser(userId);
    }

    public Set<Group> findRootGroupsByCompanyId(Long companyId) {
        return groupRepository.findAllByCompany_IdAndAndRootIsTrue(companyId);
    }

    public Optional<Group> getParent(Group child) {
        return groupToGroupRepository.findByChildGroup(child).map(GroupToGroup::getParentGroup);
    }

    public Integer deleteGroupToGroup(Group parent, Group group) {
        return groupToGroupRepository.deleteByParentGroupAndChildGroup(parent, group);
    }

    public void deleteGroupToGroupByParentIdOrChildId(Group parent, Group group) {
        groupToGroupRepository.deleteByParentGroup_IdOrChildGroup_Id(parent.getId(), group.getId());
    }

    public void deleteGroup(Group group) {
        groupRepository.deleteByIdAndCompany_Id(group.getId(), group.getCompany().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserGroupAuthByGroupId(long groupId) {
        userGroupAuthRepository.deleteByGroup_Id(groupId);
    }
}
