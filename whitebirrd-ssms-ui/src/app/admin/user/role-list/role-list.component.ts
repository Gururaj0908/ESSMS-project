import { Component, OnInit } from '@angular/core';
import { AdminListComponent } from '../../admin-list/admin-list.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuHelperService } from '../../services/menu.helper.service';
import { CrudService } from '../../services/crud.service';
import { RemoteService } from '../../../services/remote.service';
import { BusinessRouterService } from '../../services/business-router.service';
import { LanguageService } from '../../../services/language.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.scss']
})
export class RoleListComponent extends AdminListComponent implements OnInit {
  public tableData: MatTableDataSource<any>;

  constructor(
    l: LanguageService,
    public override route: ActivatedRoute,
    public override router: Router,
    brs: BusinessRouterService,
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

  managePermission(data: any) {
    sessionStorage.setItem('createdBy', data.createdBy);
    this.router.navigate(['admin/role/permission/' + data.guid + '|' + data.id]);
  }

  public applyFilter(event: any) {
	  var filterValue = (event.target as HTMLInputElement).value;
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.tableData.filter = filterValue;
  }

}
