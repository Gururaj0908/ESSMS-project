import { Injectable } from '@angular/core';
import { BusinessObject } from '../model/business.object';
import { MenuTree } from '../menu/menu.tree';
import { BusinessObjectNode } from '../menu/business.object.node';
import { Router, UrlSegment } from '@angular/router';

@Injectable()
export class MenuHelperService {

  private id: number;
  public BusinessObjects: BusinessObject[];
  constructor(private router: Router) {
  }

  public getMenuTree(businessObjects: BusinessObject[]): MenuTree {
    this.BusinessObjects = businessObjects;
    const rootElement = new BusinessObjectNode();
    const menuTree = new MenuTree();
    menuTree.setRootElement(rootElement);
    let menuItemNode = null;
    const parentNodes = new Map<string, BusinessObjectNode>();
    try {
      let count = 0;
      for (const businessObject of businessObjects) {
        if (count === 0) {
          menuItemNode = rootElement;
        } else {
          menuItemNode = new BusinessObjectNode();
        }
        const menuId = businessObject.id;
        menuItemNode.setData(businessObject);
        parentNodes.set('' + menuId, menuItemNode);
        if (count !== 0) {
          const parentNode = parentNodes.get('' + businessObject.parentId);
          if (parentNode != null) {
            parentNode.addChild(menuItemNode);
          }
        }
        count++;
      }
      parentNodes.clear();
    } catch (Exception) {
      Exception.printStackTrace();
    }
    return menuTree;
  }

  /**
    * Method to find all the menus at the specified level from the menu tree
    *
    * @param menuTree
    * @param level
    * @return
    */
  public getMenuForLevel(menuTree: MenuTree, level: number): BusinessObject[] {
    const menuItemList = new Array<BusinessObject>();
    if (menuTree != null) {
      const allMenus = menuTree.toList();
      let menuItem = null;
      for (let i = 0; i < allMenus.length; i++) {
        menuItem = allMenus[i].getData();
        if (menuItem != null && menuItem.getObjectLevel() === level) {
          menuItemList.push(menuItem);
        }
      }
    }
    // Collections.sort(menuItemList);
    return menuItemList;
  }

  /**
    * Method to find all the menus at the specified level from the menu tree
    *
    * @param menuTree
    * @param level
    * @return
    */
  public getMenuForParent(menuTree: MenuTree, parentId: number): Array<BusinessObject> {
    const menuItemList = new Array<BusinessObject>();
    const allMenus = menuTree.toList();
    let menuItem = null;
    for (let i = 1; i < allMenus.length; i++) {
      menuItem = allMenus[i].getData();
      if (menuItem != null && menuItem.parentId === parentId) {
        menuItemList.push(menuItem);
      }
    }
    return menuItemList;
  }

  /**
    * Method to get menuItem for the businessObject
    *
    * @param menuTree
    * @param level
    * @return
    */
  public getMenuNode(menuTree: MenuTree, menuObjectId: number): BusinessObject {
    const allMenus = menuTree.toList();
    return allMenus.find(t => t.data.id === menuObjectId).data;
  }

  /**
    * Method to check if a particular menu item is seleted or not given the object
    * id being accessed
    *
    * @param menuTree
    * @param objId
    * @return
    */
  public isSelected(menuTree: MenuTree, checkObjId: number, selectedObjId: number): boolean {
    const allMenus = menuTree.toList();
    let menuItem = null;
    // String hierarchyElement = "/" + checkObjId + "/";
    for (let i = 0; i < allMenus.length; i++) {
      menuItem = allMenus[i].getData();
      if (menuItem != null && menuItem.getId() === selectedObjId) {
        break;
      }
    }
    // if (menuItem != null && menuItem.getObjectHierarchy().indexOf(hierarchyElement) != -1)
    // return true;
    // else
    return false;
  }

  private LoadPathId() {
    const segments = this.GetUrlSegments();
    if (segments[0].path === 'admin') {
      if (segments[1].path === 'details') {
        if (segments[2] && segments[2].path) {
          this.id = Number(segments[2].path);
        }
      } else if (true) {
        // todo: handle other route parameter
      }
    }
  }

  public GetUrlSegments(): UrlSegment[] {
    const rt = this.router.parseUrl(this.router.url);
    const segments: UrlSegment[] = rt.root.children['primary'].segments;
    return segments;
  }

  /**
   * returns the business object on passing the id of it. If nothing is passed then current url id is used
   * @param Id The id of the business object
   */
  private GetMenuItem(Id?: number) {
    const id = Id == null ? this.id : Id;
    return this.BusinessObjects.find(t => t.id === id);
  }

  /**
   * Returns the business object based on id and parent level. if id is not passed then id from the current url is used.
   * Pass nothing as level to get this business object
   * @param Id the id of the business object whose parents needs to be found
   * @param level the level of parent you need. If nothing is passed then the business object itself is returned taking level as 0
   */
  public GetMenuItemParent(Id?: number, level?: number) {
    let ID;
    if (Id == null) {
      this.LoadPathId();
      if (this.id == null) {
        return null;
      } else {
        ID = this.id;
      }
    } else {
      ID = Id;
    }
    const menu = this.GetMenuItem(ID);
    if (!menu) {
      return null;
    }
    let parent: BusinessObject = menu;
    let id = menu.parentId;
    let index = level == null ? 0 : level;
    while (index > 0) {
      parent = this.GetMenuItem(id);
      if (parent == null) {
        throw new Error('Menu item not found');
      }
      id = parent.parentId;
      index--;
    }
    return parent;
  }



}
