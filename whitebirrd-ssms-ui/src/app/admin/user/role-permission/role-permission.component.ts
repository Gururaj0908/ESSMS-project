import { Component, OnInit, ViewChild } from '@angular/core';
import {
  TreeviewComponent, TreeviewItem, TreeviewConfig, DownlineTreeviewItem,
  OrderDownlineTreeviewEventParser, TreeviewEventParser
} from '@yberion/ngx-treeview';
import { RemoteService } from '../../../services/remote.service';
import { TreeviewService } from '../../services/treeview.service';
import * as _ from 'lodash';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleListComponent } from '../role-list/role-list.component';


@Component({
  selector: 'app-role-permission',
  templateUrl: './role-permission.component.html',
  styleUrls: ['./role-permission.component.scss'],
  providers: [
    TreeviewService,
    { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
    // { provide: TreeviewConfig, useClass: ProductTreeviewConfig }
  ]
})
export class RolePermissionComponent implements OnInit {

  dropdownEnabled = true;

  @ViewChild(TreeviewComponent) treeviewComponent: TreeviewComponent;
  items: TreeviewItem[] = [];
  roleGUID: string;
  roleId: number;
  private CreatedBy: string;

  config = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: false,
    decoupleChildFromParent: false,
    maxHeight: 500
  });

  buttonClasses = [
    'btn-outline-primary',
    'btn-outline-secondary',
    'btn-outline-success',
    'btn-outline-danger',
    'btn-outline-warning',
    'btn-outline-info',
    'btn-outline-light',
    'btn-outline-dark'
  ];
  buttonClass = this.buttonClasses[0];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private remoteService: RemoteService,
    private treeViewService: TreeviewService) { }


  ngOnInit() {
    this.CreatedBy = sessionStorage.getItem('createdBy');
    // if (this.CreatedBy == null) {
    //   // navigate to role list
    //   this.router.navigate(['admin/details/2071']);
    // }
    this.route.params.subscribe(params => {
      const parameters = params['id'].split('|', 2);
      this.roleGUID = parameters[0];
      this.roleId = parameters[1];
      this.remoteService.get('/essms-auth/businessobject/list').subscribe(
        allBOs => {
          this.remoteService.get('/essms-auth/businessobject/byroleaccess?roleGUIDs=' + this.roleGUID).subscribe(
            assignedBOs => {
              const assignedBOIds = new Array<number>();
              assignedBOs.forEach(element => {
                assignedBOIds.push(element.id);
              });
              const treeViewItem = new TreeviewItem(this.treeViewService.getTreeViewItem(allBOs.entityList,
                assignedBOIds).getRootElement());
              treeViewItem.disabled = this.CreatedBy === 'SYSTEMDEFINED';
              this.items.push(treeViewItem);
            });
        });
    });
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
    const rolePermissionRequest: any = {};
    rolePermissionRequest.roleId = this.roleId;
    rolePermissionRequest.businessObjectIds = Array.from(selectedIds);
    if (this.CreatedBy !== 'SYSTEMDEFINED') {
      this.remoteService.post('/essms-auth/rolepermission/upsert', rolePermissionRequest).subscribe(
        data => {
          // Do Nothing
        }
      );
    }
  }

}
