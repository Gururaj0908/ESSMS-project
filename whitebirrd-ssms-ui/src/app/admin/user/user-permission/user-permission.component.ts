import { Component, OnInit, ViewChild } from '@angular/core';
import {
   TreeviewItem, OrderDownlineTreeviewEventParser, TreeviewEventParser, TreeviewConfig, TreeviewComponent,
   DownlineTreeviewItem
} from '@yberion/ngx-treeview';
import { RemoteService } from '../../../services/remote.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TreeviewService } from '../../services/treeview.service';
import * as _ from 'lodash';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
   selector: 'app-user-permission',
   templateUrl: './user-permission.component.html',
   styleUrls: ['./user-permission.component.scss'],
   providers: [
      TreeviewService,
      { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
      // { provide: TreeviewConfig, useClass: ProductTreeviewConfig }
   ]
})
export class UserPermissionComponent implements OnInit {

   dropdownEnabled = true;
   roles: any[] = [];
   userPermissionRequest: any = {};
   userName: string;

   AllBusinessObjects: any;
   SelectedBusinessObjects: number[] = [];
   SelectedRoles: number[] = [];

   @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
   items: TreeviewItem[] = [];
   userGUID: string;
   branchGUID: string;

   config = TreeviewConfig.create({
      hasAllCheckBox: true,
      hasFilter: true,
      hasCollapseExpand: true,
      decoupleChildFromParent: false
   });


   constructor(
      private route: ActivatedRoute,
      public router: Router,
      private remoteService: RemoteService,
      private snackbar: MatSnackBar,
      private treeViewService: TreeviewService) { }

   ngOnInit() {
      this.route.params.subscribe(params => {
         const passedData = params['id'].split('|', 3);
         this.userGUID = passedData[0];
         this.userName = passedData[1];
         this.branchGUID = passedData[2];
         this.remoteService.get('/essms-auth/businessobject/list').subscribe(
            allBOs => {
               this.AllBusinessObjects = allBOs.entityList;
               this.remoteService.get('/essms-auth/businessobject/byuseraccess/' + this.userGUID + '/' + this.branchGUID).subscribe(
                  assignedBOs => {
                     assignedBOs.forEach(element => {
                        this.SelectedBusinessObjects.push(element.id);
                     });
                     const treeViewItem = new TreeviewItem(this.treeViewService.getTreeViewItem(this.AllBusinessObjects,
                        this.SelectedBusinessObjects).getRootElement());
                     this.items.push(treeViewItem);
                  });
            });
      });

      // get the list of available roles
      this.remoteService.get('/essms-auth/role/withpermission').subscribe(
         data => {
            // save the entity list to a variable
            data.forEach(item => {
               item.selected = false;
            });
            this.roles = data;

            this.remoteService.get('/essms-auth/userrole/byuserguids?userGUIDs=' + this.userGUID).subscribe(
               ndata => {
                  const count = this.roles.length;
                  // loop over all the available roles
                  this.roles.forEach(role => {
                     // check if this user roles in data contains this role
                     if (ndata.find(t => t.roleId === role.id)) {
                        // mark this role as selected
                        role.selected = true;
                        // add this role in list of selected roles
                        this.SelectedRoles.push(role.guid);
                     }
                  });
               }
            );
         }
      );
   }

   onSelectedChange(downlineItems: DownlineTreeviewItem[]) {
      const selectedIds = new Set<number>();
      downlineItems.forEach(downlineItem => {
         const item = downlineItem.item;
         selectedIds.add(item.value);
         let parent = downlineItem.parent;
         while (!_.isNil(parent)) {
            selectedIds.add(parent.item.value);
            parent = parent.parent;
         }
      });
      this.SelectedBusinessObjects = Array.from(selectedIds);
      if (selectedIds.size > 0) {
         console.log('RNACH :::::::::::::::::::::::::::: ' + this.branchGUID);
         this.userPermissionRequest.userGUID = this.userGUID;
         this.userPermissionRequest.roleGUIDs = this.SelectedRoles;
         this.userPermissionRequest.branchGUID = this.branchGUID;
         this.userPermissionRequest.businessObjectIds = Array.from(selectedIds);
      }
   }

   // call this method to save the permissions
   SaveChanges() {
      this.remoteService.post('/essms-auth/userpermission/upsert', this.userPermissionRequest).subscribe(
         data => {
            this.snackbar.open('Permissions Updated', null, {
               duration: 500,
            });
         }
      );
   }

   CheckboxChanged(event: any, item: any) {
      const checked: boolean = event.checked;
      const rolePerms = this.roles.find(t => t.guid === item.guid).rolePermissions;
      if (event.checked) {
         this.SelectPermissions(rolePerms);
         this.SelectedRoles.push(item.guid);
      } else {
         this.DeselectPermissions(item);
         this.SelectedRoles.splice(this.SelectedRoles.indexOf(this.SelectedRoles.find(t => t === item.guid)), 1);
      }
   }

   SelectPermissions(roles: any) {
      roles.forEach(val => {
         this.SelectedBusinessObjects.push(val.businessObject.id);
      });
      const treeViewItem = new TreeviewItem(this.treeViewService.getTreeViewItem(
         this.AllBusinessObjects, this.SelectedBusinessObjects).getRootElement());
      this.items = new Array<TreeviewItem>();
      this.items.push(treeViewItem);
   }

   DeselectPermissions(item: any) {
      // get the list of selected roles except the item
      const selectedRoles = this.roles.filter(t =>
         t.id !== item.id && this.SelectedRoles.some(r => r === t.id)
      );

      // get the list of ids of business objects that must not be deselected
      const requiredBo = new Array<number>();
      selectedRoles.forEach(t => {
         t.rolePermissions.forEach(b => {
            requiredBo.push(b.businessObject.id);
         });
      });

      // get the list of ids of business objects from the item
      const itemBo = new Array<number>();
      item.rolePermissions.forEach(t => {
         itemBo.push(t.businessObject.id);
      });

      // get the list of ids of business object that must be deselected
      const deselectBo = new Array<number>();
      itemBo.forEach(ib => {
         if (!requiredBo.some(r => r === ib)) {
            deselectBo.push(ib);
         }
      });

      // subtract the BO to be deselected from all selected list
      const newSelection = new Array<number>();
      this.SelectedBusinessObjects.forEach(t => {
         if (!deselectBo.some(r => r === t)) {
            newSelection.push(t);
         }
      });

      this.SelectedBusinessObjects = newSelection;

      // now set the all selected list to the tree
      const treeViewItem = new TreeviewItem(this.treeViewService.getTreeViewItem(
         this.AllBusinessObjects, this.SelectedBusinessObjects).getRootElement());
      this.items = new Array<TreeviewItem>();
      this.items.push(treeViewItem);
   }

   navigateToList() {
      this.router.navigate(['admin/details/' + sessionStorage.getItem('menuItemId')]);
   }
}
