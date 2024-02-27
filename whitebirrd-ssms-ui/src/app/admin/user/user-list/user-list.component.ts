import { Component, OnInit } from '@angular/core';
import { AdminListComponent } from '../../admin-list/admin-list.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuHelperService } from '../../services/menu.helper.service';
import { CrudService } from '../../services/crud.service';
import { RemoteService } from '../../../services/remote.service';
import { TableViewComponent } from '../../table-view/table-view.component';
import { BusinessRouterService } from '../../services/business-router.service';
import { LanguageService } from '../../../services/language.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent extends AdminListComponent implements OnInit {
  public tableData: MatTableDataSource<any>;

  constructor(
    l: LanguageService,
    public override route: ActivatedRoute,
    brs: BusinessRouterService,
    public override router: Router,
    public override dialog: MatDialog,
    public override menuHelperService: MenuHelperService,
    public override crudService: CrudService,
    public override remoteService: RemoteService) {
    super(l, route, brs, router, dialog, menuHelperService,
      crudService, remoteService);
  }

  override ngOnInit() {
    super.ngOnInit();
  }

  override generateTableData(url: string) {

    this.crudService.getListIncludingInactiveRecords(url)
      .subscribe(
        resp => {
          this.data = resp.body;
          if (resp.status === 200) {
            this.tableData = new MatTableDataSource<Object>(this.data.entityList);
            this.tableData.filterPredicate = this.CustomFilter;
            this.tableData.sort = this.sort;
            this.tableData.paginator = this.paginator;
            this.crudService.setTableData(this.tableData);
            this.displayableColumns = new Array();
            this.displayedHeaders = new Array();
            this.displayableHeader = this.data.displayableColumnsWithHeader;
            for (const k of Object.keys(this.data.displayableColumnsWithHeader)) {
              this.displayableColumns.push(k);
              this.displayedHeaders.push(k);
            }
            this.displayedHeaders.push('permission');
            this.displayedHeaders.push('action');
          } else {
            this.nullifyTable();
          }
        },
        error => {
          this.nullifyTable();
          console.error(error);
        });
  }

  override displayProperty(propertyName: string, row: any) {
    if (propertyName.indexOf('.') > 0) {
      return propertyName.split('.').reduce(function (prev, curr) {
        return prev ? prev[curr] : undefined;
      }, row || self);
    } else {
      return row[propertyName];
    }
  }

  managePermission(data: any) {
    if (data.userGUID) {
      this.router.navigate(['admin/user/permission/' + data.userGUID + '|' + data.name + '|' + data.branchGUID]);
    } else {
      this.router.navigate(['admin/user/permission/' + data.guid + '|' + data.registerUserModel.name + '|' + data.branchGUID]);
    }
  }

  public applyFilter(event:any) {
	var filterValue = (event.target as HTMLInputElement).value;
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.tableData.filter = filterValue;
  }

  private CustomFilter(data: any, filter: string): boolean {
    const val = TableViewComponent.FlattenRow(data, this.displayableHeader);
    return val.indexOf(filter) >= 0;
  }

}
