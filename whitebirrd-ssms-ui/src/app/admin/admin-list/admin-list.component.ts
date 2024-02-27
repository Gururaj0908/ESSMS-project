import { Component, OnInit, ViewChild, Inject, Optional, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CrudService } from '../services/crud.service';
import { FormList } from '../dynamic-form/FormList';
import { MenuHelperService } from '../services/menu.helper.service';
import { ConfirmDialogComponent, DialogData, DialogType } from '../../helpers/confirm-dialog/confirm-dialog.component';
import { RemoteService } from '../../services/remote.service';
import { SortAndPagingEventArgs } from '../table-view/SortAndPagingEventArgs';
import { OperationButtonEventArgs } from '../table-view/OperationButtonEventArgs';
import { Constants } from '../../constants';
import { BusinessObject } from '../model/business.object';
import { RouteType } from '../model/RouteType';
import { TableButtons } from '../table-view/TableButtons';
import { HyperlinkEventArgs } from '../dynamic-form/HyperlinkEventArgs';
import { TableViewComponent } from '../table-view/table-view.component';
import { BusinessRouterService } from '../services/business-router.service';
import { DetailModel } from '../popup-view/DetailModel';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-admin-list',
  templateUrl: './admin-list.component.html',
  styleUrls: ['./admin-list.component.scss']
})

export class AdminListComponent implements OnInit {
  currentUser: any;
  data: any;
  Editor: FormList;
  displayableColumns: any;
  displayedHeaders: any;
  displayableHeader: any;
  createAllowed: boolean;
  updateAllowed: boolean;
  deleteAllowed: boolean;
  activateDeactivateAllowed = false;
  deleteUrl: string;
  logUrl: string;
  hideStatusToggle = false;
  restoreUrl: string;
  activateDeactivateUrl: string;
  displayTag: string;
  Table_Data: any[];
  Table_Buttons: TableButtons[];
  Legends: DetailModel[];
  Data_Count: number;
  Search_Form: FormList;
  Remote_Paginated: Boolean;
  SearchQuery: string;
  CustomForm: boolean;
  private Param: number = null;
  private currentMenuItem: BusinessObject;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(TableViewComponent) tableCtrl: TableViewComponent;

  private _menuAction: string;

  constructor(
    public l: LanguageService,
    public route: ActivatedRoute,
    private brs: BusinessRouterService,
    public router: Router,
    public dialog: MatDialog,
    public menuHelperService: MenuHelperService,
    public crudService: CrudService,
    public remoteService: RemoteService,
    @Optional() @Inject(MAT_DIALOG_DATA) public eventArgs?: HyperlinkEventArgs
  ) {
    this.currentUser = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
  }

  ngOnInit() {
    if (this.eventArgs != null && this.eventArgs.Url != null) {
      this.generateTableData(this.eventArgs.Url);
    } else {
      this.route.params.subscribe(params => {
        this.createAllowed = false;
        this.updateAllowed = false;
        this.deleteAllowed = false;
        this.activateDeactivateAllowed = false;
        this.Table_Buttons = null;
        this.currentMenuItem = null;
        const earlierSelectedMenuItem = sessionStorage.getItem(Constants.SELECTED_MENU);
        if (params['id'] != null) {
          this.Param = Number(params['id']);
          this.currentMenuItem = this.brs.GetMenuItem(this.Param);
          sessionStorage.setItem(Constants.SELECTED_MENU, JSON.stringify(this.currentMenuItem));
          sessionStorage.removeItem(Constants.NON_URL_TABLE_DATA);
        } else {
          this.Param = null;
          if (sessionStorage.getItem(Constants.NON_URL_TABLE_DATA) == null) {
            this.currentMenuItem = JSON.parse(earlierSelectedMenuItem);
          } else {
            this.currentMenuItem = null;
          }
        }
        if (!this.currentMenuItem) {
          const data = sessionStorage.getItem(Constants.NON_URL_TABLE_DATA);
          if (data == null) {
            console.error(`404 : Not Found. We coundn't find the requested resource`, this.router.url);
            this.router.navigate(['admin/404'], { skipLocationChange: true });
            return;
          } else {
            const tabledata = JSON.parse(data);
            this.DataLoaded(tabledata);
            if (earlierSelectedMenuItem) {
              this.currentMenuItem = JSON.parse(earlierSelectedMenuItem);
              this.brs.MenuItemSelector(this.currentMenuItem);
            }
          }
        } else {
          this.brs.MenuItemSelector(this.currentMenuItem);
          this.displayTag = this.currentMenuItem.displayTag;
          const currentMenuChildren = this.currentMenuItem.children;
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
            if (currentMenuChildren[i].objectName.toUpperCase() === 'ACTIVATEDEACTIVATE') {
              this.activateDeactivateAllowed = true;
              this.activateDeactivateUrl = currentMenuChildren[i].menuAction;
            }
          }
          this._menuAction = this.currentMenuItem.menuAction;
          this.generateTableData(this.currentMenuItem.menuAction);
        }
      });
    }
  }

  generateTableData(url: string) {
    const params = {};
    params['includeDeActive'] = true;
    params['pageSize'] = 10;
    this.remoteService.get(url, new HttpParams({ fromObject: params }))
      .subscribe(
        resp => this.DataLoaded(resp),
        () => {
          this.nullifyTable();
        });
  }

  private DataLoaded(resp) {
    {
      this.Table_Data = resp.entityList;
      if (resp.tableButtons) {
        this.Table_Buttons = [];
        resp.tableButtons.forEach(b => {
          this.Table_Buttons.push(new TableButtons(b));
        });
      }
      if (resp.detailModels) {
        this.Legends = [];
        resp.detailModels.forEach(b => {
          this.Legends.push(new DetailModel(b));
        });
      } else {
        this.Legends = [];
      }
      if (resp.formList) {
        this.Editor = new FormList(resp.formList);
      }
      if (resp.customForm) {
        this.CustomForm = true;
      } else {
        this.CustomForm = false;
      }
      this.Data_Count = resp.recordCount;
      this.logUrl = resp.timelineUrl;
      if (resp.searchFormList) {
        this.Search_Form = new FormList(resp.searchFormList);
      } else {
        this.Search_Form = null;
      }
      this.Remote_Paginated = resp.isRemotePaginated;
      this.hideStatusToggle = resp.hideActiveToggle;
      this.crudService.setTableData(this.Table_Data);
      this.displayableColumns = new Array();
      this.displayedHeaders = new Array();
      this.displayableHeader = resp.displayableColumnsWithHeader;
      if (!this.displayableHeader) {
        console.error('API is not formed properly. Column Headers are missing');
        return;
      }
      for (const k of Object.keys(resp.displayableColumnsWithHeader)) {
        this.displayableColumns.push(k);
        this.displayedHeaders.push(k);
      }
      if (this.activateDeactivateAllowed) {
        this.displayedHeaders.push('status');
      }
      if (this.updateAllowed || this.deleteAllowed) {
        this.displayedHeaders.push('action');
        if (this.deleteAllowed) {
          this.displayedHeaders.push('restore');
        }
      }
    }
  }

  navigateToForm(editorForm: FormList) {
    sessionStorage.removeItem(Constants.ENTITY_FORM_DATA);
    sessionStorage.setItem(Constants.FORM_EDITOR, JSON.stringify(editorForm));
    this.router.navigate(['admin/form']);
  }

  nullifyTable() {
    this.Table_Data = null;
    this.displayableColumns = [];
    this.displayableHeader = {};
  }


  editRecord(data) {
    sessionStorage.setItem(Constants.ENTITY_FORM_DATA, JSON.stringify(data));
    sessionStorage.setItem(Constants.FORM_EDITOR, JSON.stringify(this.Editor));
    this.router.navigate(['admin/form']);
  }

  confirmDelete(data) {
    this.openDialog(this.l.GetString('Delete'), this.l.GetString('SureDelete'), this.l.GetString('Yes'), this.l.GetString('No'),
      DialogType.Alert, () =>
        this.crudService.delete(this.deleteUrl, this.restoreUrl, data.id, this.RefreshData, this), data.id);
  }

  openDialog(title: string, message: string, affirmText: string, closeText: string, type: DialogType,
    callback: (id: string) => void, callback_parameter: string): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: new DialogData(title, message, affirmText, closeText, type)
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        callback(callback_parameter);
      }
    });
  }

  activateDeactivate(event: any) {
    const id = event.DataRow.id;
    const status = event.NewStatus.checked;
    this.crudService.activateDeactivate(this.activateDeactivateUrl, id, status);
  }

  restore(event: any) {
    const id = event.DataRow.id;
    const restore = event.NewStatus.checked;
    this.crudService.restore(this.restoreUrl, id, restore);
  }

  private RefreshData(context: any) {
    if (context.Table_Data) {
      context.Table_Data = JSON.parse(JSON.stringify(context.Table_Data));
    }
  }


  displayProperty(propertyName: string, row: any) {
    if (propertyName.indexOf('.') > 0) {
      return propertyName.split('.').reduce(function (prev, curr) {
        return prev ? prev[curr] : undefined;
      }, row || self);
    } else {
      return row[propertyName];
    }
  }

  public SearchCleared() {
    this.SearchQuery = null;
    this.ExecuteRemoteCall();
  }

  public Search(query: any) {
    this.ExecuteRemoteCall(JSON.stringify(query.SearchData), query.PageSize);
  }

  public PageChanged(args: SortAndPagingEventArgs) {
    this.ExecuteRemoteCall(JSON.stringify(args.SearchQuery), args.PageSize,
      args.PageNumber, args.SortColumn, args.SortDirection === 'desc');
  }

  private ExecuteRemoteCall(query?: string, pageSize?: Number, pageNumber?: Number, sortBy?: string, sortDesc?: boolean) {
    if (this.Search_Form) {
      const url = this.Search_Form.url;
      let param = new HttpParams();
      if (query) {
        param = param.append('jsonQueryString', query);
      } else {
        this.SearchQuery = null;
      }
      if (pageNumber) {
        param = param.append('pageNumber', pageNumber.toString());
      }
      if (pageSize) {
        param = param.append('pageSize', pageSize.toString());
      }
      if (sortBy) {
        param = param.append('orderBy', sortBy);
      }
      if (sortDesc) {
        param = param.append('desc', 'true');
      }
      this.remoteService.get(url, param).subscribe(
        data => {
          this.Table_Data = data.entityList;
          this.Data_Count = data.recordCount;
          // check if the received data has table buttons.
          if (data.tableButtons) {
            this.Table_Buttons = [];
            data.tableButtons.forEach((b: TableButtons) => {
              this.Table_Buttons.push(new TableButtons(b));
            });
          }
        },
        error => {
          this.remoteService.logInformationToConsole(true, true, 'An error occurred while requesting: ', error);
        }
      );
    }
  }

  public BottomOperationExecuted(event: OperationButtonEventArgs) {
    if (event && event.Url) {
      if (event.HttpMethod && event.HttpMethod.toUpperCase() === 'GET') {
        let params = new HttpParams();
        for (let i = 0; i < event.Data.length; i++) {
          const element = event.Data[i];
          const keys = Object.keys(element);
          keys.forEach(key => {
            const k = event.IsSingleSelection ? key : `${key}[${i}]`;
            const v = element[key];
            params = params.append(k, v);
          });
        }
        this.remoteService.get(event.Url, params).subscribe(data => {
          this.CompleteBottomOps(data, event.Navigate, event.RouteType);
        }, error => {
          this.remoteService.logInformationToConsole(true, true, 'Error getting form: ', error);
        });
      } else {
        const isDownload = event.ButtonOperation === 'File' || event.ButtonOperation === 'Print';

        this.remoteService.execute(event.HttpMethod, event.Url, event.IsSingleSelection ? event.Data[0] : event.Data,
          null, event.Form, isDownload).subscribe(
            data => this.CompleteBottomOps(data, event.Navigate, event.RouteType),
            error => this.remoteService.logInformationToConsole(true, true, 'Error getting form: ', error)
          );
      }
    } else {
      if (event.ButtonOperation === 'Print') {
        window.print();
      }
      // TODO: Add an option for navigation to BO without remote call.
    }
  }

  private CompleteBottomOps(data, navigate: boolean, route: RouteType, operation?: string) {
    if (navigate) {
      if (route != null) {
        switch (route) {
          case RouteType.List:
            this.DataLoaded(data.body == null ? data : data.body);
            break;
          case RouteType.Tree:
            break;
          case RouteType.UI:
            let d = new BusinessObject(data.body == null ? data : data.body);
            d = this.brs.GetMenuItem(d.id);
            if (d.id === this.Param) {
              this.ReloadData();
            } else {
              this.brs.HandleNavigation(d, true, true);
            }
            break;
          case RouteType.Form:
          default:
            this.navigateToForm(data.body == null ? data : data.body);
            break;
        }
      } else {
        this.navigateToForm(data.body == null ? data : data.body);
      }
    } else if (operation) {
      const contentType = data.headers.get('Content-Type');
      const file = new Blob([data.body], { type: contentType });
      const url = URL.createObjectURL(file);
      switch (operation) {
        case 'Print':
          const printWindow = window.open(url, '_blank', 'width=800,height=500');
          printWindow.print();
          break;
        case 'File':
        default:
          const a = document.createElement('a');
          a.href = url;
          a.click();
          break;
      }
    } else {
      this.ReloadData();
    }
  }

  public ReloadData() {
    this.generateTableData(this._menuAction);
  }

  public DialogButtonClicked() {
    const d = this.tableCtrl.SelectedFormGroups[0];
    this.eventArgs.Control.setValue(d.get('value').value);
  }

}
