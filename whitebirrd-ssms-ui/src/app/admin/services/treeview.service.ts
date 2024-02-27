import { BusinessObject } from '../model/business.object';
import { TreeViewItemNode } from '../treeview/tree.view.item.node';
import { TreeViewMenuTree } from '../treeview/tree.view.menu.tree';
import { Injectable } from '@angular/core';


@Injectable()
export class TreeviewService {

  constructor() { }

  public getTreeViewItem(allBusinessObjects: BusinessObject[], assignedBOIds: number[]): TreeViewMenuTree {
    const rootElement = new TreeViewItemNode();
    const menuTree = new TreeViewMenuTree();
    menuTree.setRootElement(rootElement);
    let menuItemNode = null;
    const parentNodes = new Map<number, TreeViewItemNode>();
    let count = 0;
    for (const businessObject of allBusinessObjects) {
      if (count === 0) {
        menuItemNode = rootElement;
      } else {
        menuItemNode = new TreeViewItemNode();
      }
      const menuId = businessObject.id;
      menuItemNode.text = businessObject.objectName;
      menuItemNode.value = businessObject.id;
      if (assignedBOIds.includes(businessObject.id)) {
        menuItemNode.checked = true;
      } else {
        menuItemNode.checked = false;
      }
      parentNodes.set(menuId, menuItemNode);
      if (count !== 0) {
        const parentNode = parentNodes.get(businessObject.parentId);
        if (parentNode != null) {
          parentNode.addChild(menuItemNode);
        }
      }
      count++;
    }
    parentNodes.clear();
    return menuTree;
  }
}


