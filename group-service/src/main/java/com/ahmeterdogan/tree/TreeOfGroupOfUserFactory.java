package com.ahmeterdogan.tree;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TreeOfGroupOfUserFactory {
    private final ApplicationContext applicationContext;

    public TreeOfGroupOfUserFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public TreeOfGroupOfUser createTreeOfGroupOfUser() {
        return applicationContext.getBean(TreeOfGroupOfUser.class);
    }
}