import { Component, OnInit, ViewChild } from '@angular/core';
import { Route, Router, ActivatedRoute } from '@angular/router';
import { RemoteService } from '../../services/remote.service';
import { MenuHelperService } from '../services/menu.helper.service';
import { Constants } from '../../constants';
import { BusinessObject } from '../model/business.object';
import { FormList } from '../dynamic-form/FormList';
import { HttpParams } from '@angular/common/http';
import { TreeAreaComponent } from '../tree-area/tree-area.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TreeViewActionEventArgs } from '../mbo/tree-view/TreeViewActionEventArgs';
import { environment } from '../../../environments/environment';

@Component({
   selector: 'app-admin-tree',
   templateUrl: './admin-tree.component.html',
   styleUrls: ['./admin-tree.component.scss']
})
export class AdminTreeComponent implements OnInit {
   displayTag: string;
   deleteUrl: string;
   restoreUrl: string;
   public Data;
   public Form: FormList;
   createAllowed = false;
   updateAllowed = false;
   deleteAllowed = false;
   titleKey: string;
   subtitleKey: string;
   tooltipKey: string;
   parentIdKey: string;
   disabled: boolean;
   showChildren: boolean;
   selectionAllowed: boolean;
   PostUrl: string;
   private _queryParam;
   CreateUrl: string;
   UpdateUrl: string;
   DeleteUrl: string;
   IsDebug = false;

   @ViewChild('tree', {static: false}) Treeview: TreeAreaComponent;

   constructor(private remote: RemoteService,
      private router: Router,
      private menuHelperService: MenuHelperService,
      private snack: MatSnackBar,
      private route: ActivatedRoute) { }

   ngOnInit() {
      this._queryParam = (this.route.queryParams as any).value;
      this.IsDebug = !environment.production;
      this.route.params.subscribe(param => {
         const businessObjects = JSON.parse(sessionStorage.getItem(Constants.MENU_ITEMS));
         const menuTree = this.menuHelperService.getMenuTree(businessObjects);
         let currentMenuItem;
         if (param != null) {
            const item = Number(param["id"]);
            currentMenuItem = this.menuHelperService.getMenuNode(menuTree, item);
            sessionStorage.setItem(Constants.SELECTED_MENU, JSON.stringify(currentMenuItem));
         } else {
            currentMenuItem = JSON.parse(sessionStorage.getItem(Constants.SELECTED_MENU)) as BusinessObject;
         }
         if (!currentMenuItem) {
            console.error(`404 : Not Found. We coundn't find the requested resource`, this.router.url);
            this.router.navigate(['admin/404'], { skipLocationChange: true });
            return;
         }
         this.displayTag = currentMenuItem.displayTag;
         const currentMenuChildren = this.menuHelperService.getMenuForParent(menuTree, currentMenuItem.id);
         for (let i = 0; i < currentMenuChildren.length; i++) {
            if (currentMenuChildren[i].objectName.toUpperCase() === 'CREATE') {
               this.createAllowed = true;
               sessionStorage.setItem(Constants.CREATE_URL, currentMenuChildren[i].menuAction);
            }
            if (currentMenuChildren[i].objectName.toUpperCase() === 'UPDATE') {
               this.updateAllowed = true;
               sessionStorage.setItem(Constants.UPDATE_URL, currentMenuChildren[i].menuAction);
            }
            if (currentMenuChildren[i].objectName.toUpperCase() === 'DELETE') {
               this.deleteAllowed = true;
               const menuAction = currentMenuChildren[i].menuAction;
               if (menuAction) {
                  this.deleteUrl = menuAction;
                  this.restoreUrl = menuAction.substring(0, menuAction.lastIndexOf('/')) + '/restore';
               }
            }
         }
         if (!currentMenuItem) {
            console.error(`404 : Not Found. We coundn't find the requested resource`, this.router.url);
            this.router.navigate(['admin/404'], { skipLocationChange: true });
            return;
         }
         this.displayTag = currentMenuItem.displayTag;
         this.getData(currentMenuItem.menuAction, this._queryParam);
      });
   }

   private getData(url: string, query) {
      const param = new HttpParams({ fromObject: query });
      this.remote.get(url, param).subscribe(
         data => {
            this.titleKey = data.configuration.titleKey;
            this.subtitleKey = data.configuration.subtitleKey;
            this.parentIdKey = data.configuration.parentIdKey;
            this.tooltipKey = data.configuration.tooltipKey;
            this.selectionAllowed = data.configuration.selectionAllowed;
            this.Form = new FormList(data.formList);
            this.Data = data.businessObjectModel;
            this.disabled = data.configuration.disabled;
            this.showChildren = data.configuration.showChildrenCount;
            this.PostUrl = data.configuration.submitUrl;
            this.CreateUrl = data.configuration.createUrl;
            this.UpdateUrl = data.configuration.updateUrl;
            this.DeleteUrl = data.configuration.deleteUrl;
         },
         err => {
            this.remote.logInformationToConsole(true, true, 'Error getting tree data', err);
         }
      );
   }


   public SaveChild(data: TreeViewActionEventArgs) {
      this.remote.post(this.CreateUrl, data.NodeItem, data.Form).subscribe(
         d => {
            if (data.Sender.TreeData.children == null) {
               data.Sender.TreeData.children = [];
            }
            data.Sender.TreeData.children.push(d);
            data.DialogRef.close();
         },
         e => this.snack.open(`Couldn't save the permission`, null,
            { duration: 2000, verticalPosition: 'top', horizontalPosition: 'end', politeness: 'polite' })
      );
   }

   public UpdateNode(data: TreeViewActionEventArgs) {
      this.remote.put(this.UpdateUrl, data.NodeItem, data.Form).subscribe(
         d => {
            data.Sender.TreeData = d;
            data.DialogRef.close();
         },
         e => this.snack.open(`Couldn't save the permission`, null,
            { duration: 2000, verticalPosition: 'top', horizontalPosition: 'end', politeness: 'polite' })
      );
   }

   public DeleteNode(data: TreeViewActionEventArgs) {
      this.remote.delete(this.DeleteUrl, [data.NodeItem.id], true).subscribe(
         d => {

         }
      );
   }

   public SubmitClicked() {
      const postdata: any = {};
      postdata.ids = this.Treeview.SelectedIds;
      if (this._queryParam != null) {
         const keys = Object.keys(this._queryParam);
         keys.forEach(k => {
            postdata[k] = this._queryParam[k];
         });
      }
      this.remote.post(this.PostUrl, postdata).subscribe(
         d => this.snack.open('Permission Saved', null, { duration: 2500, politeness: 'polite' }),
         e => this.snack.open(`Couldn't save the permission`, null,
            { duration: 2000, verticalPosition: 'top', horizontalPosition: 'end', politeness: 'polite' })
      );
   }
}
