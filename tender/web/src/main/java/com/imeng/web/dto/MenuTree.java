package com.imeng.web.dto;

import com.imeng.common.model.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wwj
 * 2020/1/19 19:59
 */
public class MenuTree {

    private List<Menu> menuList = new ArrayList<Menu>();

    public MenuTree(List<Menu> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<Menu> builTree() {
        List<Menu> treeMenus = new ArrayList<Menu>();
        for (Menu menuNode : getRootNode()) {
            menuNode = buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private Menu buildChilTree(Menu pNode) {
        List<Menu> chilMenus = new ArrayList<Menu>();
        for (Menu menuNode : menuList) {
            if (menuNode.getPId()==pNode.getId()) {
                chilMenus.add(buildChilTree(menuNode));
            }
        }
        pNode.setSubMenus(chilMenus);
        return pNode;
    }

    //获取根节点
    private List<Menu> getRootNode() {
        List<Menu> rootMenuLists = new ArrayList<Menu>();
        for (Menu menuNode : menuList) {
            if (menuNode.getPId()==0l) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }
}
