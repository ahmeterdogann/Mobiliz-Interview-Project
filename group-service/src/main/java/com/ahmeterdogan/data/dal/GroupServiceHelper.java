package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.repository.IGroupRepository;
import com.ahmeterdogan.data.repository.IGroupToGroupRepository;
import com.ahmeterdogan.data.repository.IUserGroupAuthRepository;
import org.springframework.stereotype.Component;
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


    public Group getParent(Group group) {
        return groupToGroupRepository.findParent(group.getId());
    }

    public Set<Group> getChildren(Group group) {
        return groupToGroupRepository.findAllByParentGroup(group.getId());
    }

    public Set<Group> getUserGroupAuth(long userId) {
        return userGroupAuthRepository.findAllGroupsByUser(userId);
    }



//    public List<GroupVehicleDTO> getAllVehiclesOfUserByGrouping(User user) {
//        List<Group> userGroups =  userGroupAuthRepository.findAllGroupsByUser(user.getId());
//        return buildTree(userGroups);
//    }

//    private List<GroupTreeNode> buildTree(List<Group> groups) {
//        List<GroupTreeNode> tree = new ArrayList<>();
//
//        for (Group group : groups) {
//            if (group.isRoot()) {
//                tree.add(new GroupTreeNode(group.getId(), group.getName()));
//                List<Group>
//            }
//            else {
//                Group parent = getParent(group);
//            }
//
//        }
//    }
}
