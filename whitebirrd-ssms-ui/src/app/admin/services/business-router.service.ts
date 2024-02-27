import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BusinessObject } from '../model/business.object';
import { RouteType } from '../model/RouteType';
import { Constants } from '../../constants';
import { RemoteService } from '../../services/remote.service';
import { GlobalService } from '../../services/global.service';

@Injectable({
  providedIn: 'root'
})
export class BusinessRouterService {

  public MainMenu: BusinessObject[];


  private _SelectedPrimaryMenu: BusinessObject;
  public get SelectedPrimaryMenu(): BusinessObject {
    return this._SelectedPrimaryMenu;
  }
  public set SelectedPrimaryMenu(v: BusinessObject) {
    this._SelectedPrimaryMenu = v;
    this.updateTitle();
  }


  public get SelectedPrimaryMenuIndex(): number {
    if (this.MainMenu == null || this.SelectedPrimaryMenu == null) {
      return 0;
    }
    return this.MainMenu.findIndex(t => t.id === this.SelectedPrimaryMenu.id);
  }

  public _SelectedSecondaryMenu: BusinessObject;

  public get SelectedSecondaryMenu(): BusinessObject {
    return this._SelectedSecondaryMenu;
  }
  public set SelectedSecondaryMenu(v: BusinessObject) {
    this._SelectedSecondaryMenu = v;
    this.updateTitle();
  }

  private _SelectedTertiaryMenu: BusinessObject;
  public get SelectedTertiaryMenu(): BusinessObject {
    return this._SelectedTertiaryMenu;
  }
  public set SelectedTertiaryMenu(v: BusinessObject) {
    this._SelectedTertiaryMenu = v;
    this.updateTitle();
  }

  public EnableSecondaryMenu = this.SelectedSecondaryMenu != null;
  public ShowSecondaryMenu: boolean;
  public LoggedUser: any;

  constructor(private router: Router, private remote: RemoteService, private gs: GlobalService) {
  }

  private updateTitle() {
    if (this.SelectedTertiaryMenu) {
      this.gs.SetTitle(this.SelectedTertiaryMenu.displayTag);
    } else if (this.SelectedPrimaryMenu) {
      this.gs.SetTitle(this.SelectedPrimaryMenu.displayTag);
    } else {
      this.gs.SetTitle('eSSMS');
    }
  }

  public HandleNavigation(bo: BusinessObject, saveInSession = false, handleSelection = true) {
    if (saveInSession) {
      sessionStorage.setItem(Constants.SELECTED_MENU, JSON.stringify(bo));
    }
    switch (bo.routeType) {
      case RouteType.List:
        this.router.navigate(['admin/details/' + bo.id]);
        break;
      case RouteType.Tree:
        this.router.navigate(['admin/tree/' + bo.id]);
        break;
      case RouteType.Form:
        this.router.navigate(['admin/form/' + bo.id]);
        break;
      case RouteType.UI:
        this.router.navigate([bo.menuAction]);
        break;
      default:
        console.error('Route type in business object is missing');
        break;
    }
    if (handleSelection) {
      this.MenuItemSelector(bo);
    }
  }

  async FetchBusinessObjects() {
    const loginInfo = sessionStorage.getItem(Constants.LOGGED_USER);
    if (loginInfo != null) {
      this.LoggedUser = JSON.parse(loginInfo);
    }
    const menuItems = sessionStorage.getItem(Constants.MENU_ITEMS);
    if (menuItems == null) {
      const data = await this.remote.getAsync('/essms-auth/businessobject/byuseraccess');
      if (Array.isArray(data.body)) {
        const d: BusinessObject[] = [];
        data.body.forEach(dd => d.push(new BusinessObject(dd)));
        sessionStorage.setItem(Constants.MENU_ITEMS, JSON.stringify(d));
        this.GenerateMenuTree(d);
      }
    }
    const menuItemsJson = JSON.parse(menuItems);
    if (menuItemsJson) {
      this.GenerateMenuTree(menuItemsJson);
    }
  }

  public GenerateMenuTree(menuData: BusinessObject[]) {
    const root = menuData.find(t => t.objectLevel === 1);
    this.LoadChildren(root, menuData);
    this.MainMenu = root.children;
  }

  private LoadChildren(item: BusinessObject, data: BusinessObject[]) {
    const children = data.filter(t => t.parentId === item.id).sort((a, b) => a.displayOrder - b.displayOrder);
    item.children = children;
    children.forEach(child => {
      this.LoadChildren(child, data);
    });
  }

  public MainMenuSelected(bo: BusinessObject) {
    this.SelectedPrimaryMenu = bo;
    this.SelectedSecondaryMenu = null;
    this.SelectedTertiaryMenu = null;
    if (bo.children && bo.children.length > 0) {
      bo.children.forEach(child => {
        if (!child.isHidden) {
          this.SelectedSecondaryMenu = child;
          this.SelectedTertiaryMenu = child.children?child.children[0]:null;
          return;
        }
      });
    }
    this.HandleNavigation(this.SelectedTertiaryMenu == null ? this.SelectedPrimaryMenu : this.SelectedTertiaryMenu, true, false);
  }

  public MenuItemSelector(bo: BusinessObject) {
    if (bo.objectLevel === 2) {
      this.SelectedPrimaryMenu = bo;
      this.SelectedSecondaryMenu = null;
      this.SelectedTertiaryMenu = null;
    } else if (bo.objectLevel > 3) {
      this.SelectedPrimaryMenu = this.GetParentMenuItem(bo, 2);
      this.SelectedSecondaryMenu = this.GetParentMenuItem(bo, 3);
      this.SelectedTertiaryMenu = bo;
    }
  }

  public GetMenuItem(id: number) {
    return this.findMenuFromArray(id, this.MainMenu);
  }

  private findMenuFromArray(id: number, data: BusinessObject[]) {
    if (data == null || data.length === 0) {
      return null;
    }
    const p = data.find(t => t.id === id);
    if (p) {
      return p;
    }
    for (let i = 0; i < data.length; i++) {
      const b = this.findMenuFromArray(id, data[i].children);
      if (b) {
        return b;
      }
    }
  }

  private GetParentMenuItem(bo: BusinessObject, level: number) {
    let currentItem = bo;
    if (currentItem.objectLevel < level) {
      return null;
    }
    while (currentItem.objectLevel > level) {
      currentItem = this.GetParent(currentItem, this.MainMenu);
    }
    return currentItem;
  }

  private GetParent(bo: BusinessObject, data: BusinessObject[]): BusinessObject {
    const p = data.find(t => t.id === bo.parentId);
    if (p) {
      return p;
    }
    for (let i = 0; i < data.length; i++) {
      const b = this.GetParent(bo, data[i].children);
      if (b) {
        return b;
      }
    }
    return null;
  }

}
