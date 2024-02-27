import { ToggledEventArgs } from './ToggledEventArgs';
import { SortAndPagingEventArgs } from './SortAndPagingEventArgs';
import { ClickedEventArgs } from './ClickedEventArgs';
import { KeyValuePair } from './KeyValuePair';
import { TableButtons } from './TableButtons';
import { OperationButtonEventArgs } from './OperationButtonEventArgs';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild, AfterViewInit, DoCheck, OnChanges, SimpleChanges } from '@angular/core';
import { FormEditorModel } from '../dynamic-form/FormEditor';
import { FormList } from '../dynamic-form/FormList';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { PopupViewComponent } from '../popup-view/popup-view.component';
import { PopupType } from '../popup-view/PopupType';
import { PopupDataArgs } from '../popup-view/PopupDataArgs';
import { Formatter } from '../popup-view/Formatter';
import { CoreFormOps } from '../mbo/form-area/core-form-ops';
import { SelectionEventArgs } from '../dynamic-form/dynamic-form.component';
import { HyperlinkEventArgs } from '../dynamic-form/HyperlinkEventArgs';
import { environment } from '../../../environments/environment';
import { RemoteService } from '../../services/remote.service';
import { HttpParams } from '@angular/common/http';
import { TimeLineModel, TimeLineViewComponent } from '../mbo/time-line-view/time-line-view.component';
import { FormAreaComponent } from '../mbo/form-area/form-area.component';
import { RouteType } from '../model/RouteType';
import { AdminListComponent } from '../admin-list/admin-list.component';
import { VisibilityOperators } from '../mbo/enum/VisibilityOperators';
import { DetailModel } from '../popup-view/DetailModel';
import { LanguageService } from '../../services/language.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.scss']
})
export class TableViewComponent implements OnInit, DoCheck, OnChanges, AfterViewInit {

  //#region Static variables
  private static Columns_static: string[];
  //#endregion

  //#region Input Variables
  @Input() TABLE_DATA: any[];
  @Input() Columns: any;
  @Input() PageSize;
  @Input() Operations: TableButtons[];
  @Input() Legends: DetailModel[];
  @Input() ShowStatusToggle: boolean;
  @Input() ShowEditButton: boolean;
  @Input() ShowDeleteButton: boolean;
  @Input() TimeLineUrl: string;

  @Input() RemoteOperations: boolean;
  @Input() TotalRowCount: number;
  @Input() SearchForm: FormList;
  @Input() ShowCreateButton: boolean;
  @Input() ShowFilterButton = true;
  @Input() HideActiveToggler = false;
  @Input() CustomForm: boolean;
  //#endregion

  //#region Output Events
  @Output() SortingOrPagingExecuted = new EventEmitter<SortAndPagingEventArgs>();
  @Output() SearchExecuted = new EventEmitter<any>();
  @Output() SearchCleared = new EventEmitter();
  @Output() DeleteButtonClicked = new EventEmitter<ClickedEventArgs>();
  @Output() EditButtonClicked = new EventEmitter<ClickedEventArgs>();
  @Output() StatusToggled = new EventEmitter<ToggledEventArgs>();
  @Output() RestoreToggled = new EventEmitter<ToggledEventArgs>();
  @Output() OperationExecuted = new EventEmitter<OperationButtonEventArgs>();
  @Output() CreateClicked = new EventEmitter();
  @Output() ReloadData = new EventEmitter();
  //#endregion

  //#region View children
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(FormAreaComponent) SearchFormArea: FormAreaComponent;
  //#endregion

  //#region Variables
  public ColumnHeaders: any[] = [];
  public PageSizeOptions = [10, 25, 50, 100];
  public PageSizeLocal = 10;
  public SortDirection:string = 'asc';
  public SortColumn: string;
  public DataSource: MatTableDataSource<any>;
  public ShownColumns: string[] = [];
  public SelectedFormGroups: FormGroup[] = [];
  public Form: FormArray;
  public Group: FormGroup;
  public IsDevelopmentMode: boolean;
  public ShowFilter = false;
  public SelectionMode = 0;
  private SearchData = {};
  public RM = RemoteService;
  //#endregion

  // for filter
  static FlattenRow(data: any, keys: string[]): string {
    let val = '';
    keys.forEach(k => {
      let d = TableViewComponent.GetValue(data, k);
      if (d.formEditor) {
        d = d.formEditor.initialValue;
      }
      val += d;
    });
    return val.toLowerCase();
  }

  static GetValue(row, name) {
    name = name.split('.');
    for (let i = 0; i < name.length; i++) {
      if (row[name[i]] === undefined || row[name[i]] == null) {
        return '';
      }
      row = row[name[i]];
    }
    return row;
  }

  constructor(
    public l: LanguageService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private remote: RemoteService,
    private snack: MatSnackBar,
  ) {
    this.IsDevelopmentMode = !environment.production;
    if (!this.PageSize) {
      this.PageSize = 10;
    }
  }

  ngOnInit() {
    if(this.paginator){
      this.paginator._intl.itemsPerPageLabel = this.l.GetString('ItemsPerPage');
      this.paginator._intl.getRangeLabel = (page, pageSize, Length) => {
        const start = page * pageSize + 1;
        let end = start + pageSize - 1;
        if (end > Length) {
          end = Length;
        }
        return this.l.GetString('PageNoShow', start, end, Length);
      };
      this.paginator._intl.firstPageLabel = this.l.GetString('FirstPage');
      this.paginator._intl.previousPageLabel = this.l.GetString('PreviousPage');
      this.paginator._intl.nextPageLabel = this.l.GetString('NextPage');
      this.paginator._intl.lastPageLabel = this.l.GetString('LastPage');
    }
    this.UpdateDataSource();
    this.CheckSelectionMode();
  }

  ngAfterViewInit(): void {

  }

  ngOnChanges(changes: SimpleChanges): void {
    const key = Object.keys(changes);
    if (key.some(t => t === 'Columns')) {
      this.LoadColumnInfo();
      this.SelectedFormGroups = [];
      this.ShowFilter = false;
      this.SearchData = {};
      this.paginator?.firstPage();
    }
    if (key.some(t => t === 'TABLE_DATA')) {
      this.LoadTableData();
    }
    if (key.some(t => t === 'PageSize')) {
      if (this.PageSize) {
        if (!this.PageSizeOptions.some(t => t === this.PageSize)) {
          this.PageSizeOptions.push(this.PageSize);
        }
        this.PageSizeLocal = this.PageSize;
      }
    }
    if (key.some(t => t === 'Operations')) {
      if (this.Operations) {
        if (typeof this.Operations === 'string') {
          this.Operations = JSON.parse(this.Operations);
        }
      }
    }
    if (key.some(t => t === 'Legends')) {
      if (this.Legends) {
        if (typeof this.Legends === 'string') {
          this.Legends = JSON.parse(this.Legends);
        }
      }
    }
    if (key.some(t => t === 'ShowStatusToggle')) {
      if (this.ShowStatusToggle) {
        this.ShownColumns.push('status');
      } else {
        this.ShownColumns.splice(this.ShownColumns.indexOf('status'), 1);
      }
    }
    if (key.some(t => t === 'ShowDeleteButton' || t === 'ShowEditButton')) {
      if (this.ShowDeleteButton || this.ShowEditButton) {
        this.ShownColumns.push('action');
        if (this.ShowDeleteButton) {
          this.ShownColumns.push('restore');
          console.log('Pushing restore ******************');
        }
      } else {
        this.ShownColumns.splice(this.ShownColumns.indexOf('action'), 1);
      }
    }
  }

  ngDoCheck(): void {
    // this.UpdateDataSource();
  }

  StatusToggledEventHandler(event, data) {
    const args = new ToggledEventArgs();
    args.DataRow = data;
    args.NewStatus = event;
    this.StatusToggled.emit(args);
  }

  RestoreToggledEventHandler(event, data) {
    const args = new ToggledEventArgs();
    args.DataRow = data;
    args.NewStatus = event;
    this.RestoreToggled.emit(args);
  }

  EditClicked(data) {
    const args = new ClickedEventArgs();
    args.DataRow = data;
    this.EditButtonClicked.emit(args);
  }

  DeleteClicked(data) {
    const args = new ClickedEventArgs();
    args.DataRow = data;
    this.DeleteButtonClicked.emit(args);
  }

  private EmitSortPageChange() {
    const sap = new SortAndPagingEventArgs();
    if (this.sort.active === 'SortColumn') {
      sap.SortColumn = null;
    } else {
      sap.SortColumn = this.sort.active;
    }
    sap.SortDirection = this.sort.direction;
    sap.PageNumber = this.paginator.pageIndex + 1;
    sap.PageSize = this.paginator.pageSize;
    this.PageSize = this.paginator.pageSize;
    sap.SearchQuery = this.SearchData;
    this.SortingOrPagingExecuted.emit(sap);
  }

  applyFilter(event: any) {
	var filterValue = (event.target as HTMLInputElement).value;
    filterValue = filterValue.trim().toLowerCase();
    this.DataSource.filter = filterValue;
  }

  private CustomFilter(data: any, filter: string): boolean {
    const val = TableViewComponent.FlattenRow(data, TableViewComponent.Columns_static);
    return val.indexOf(filter) >= 0;
  }

  public ExecuteSearch(data: FormGroup) {
    const d = data.getRawValue();
    const k = Object.keys(d);
    k.forEach(key => {
      if (d[key] != null && d[key] !== undefined) {
        this.SearchData[key] = d[key];
      }
    });
    this.SearchExecuted.emit({ SearchData: this.SearchData, PageSize: this.paginator.pageSize });
    this.SearchData = {};
    this.sort.active = null;
    this.sort.direction = 'asc';
    // this.paginator.pageSize = this.PageSize;
    this.paginator.pageIndex = 0;
  }

  public ExecutedClear() {
    this.SearchData = {};
    this.SearchCleared.emit();
  }


  OpenDialog(args: HyperlinkEventArgs) {
    if (args.Route === RouteType.List) {
      const dialog = this.dialog.open(AdminListComponent, { width: '80%', data: args, disableClose: args.ReturnValue });
      dialog.afterClosed().subscribe(t => {
        console.log('dialog closed : ', t);
      });
    } else {
      this.dialog.open(PopupViewComponent, {
        // width: '80%',
        data: new PopupDataArgs(args.Url, null, null, PopupType.Remote, null, null, null, null, args.Route)
      });
    }
  }

  // called when bottom button is clicked
  NavigateToURL(button: TableButtons) {
    if (this.CustomForm) {
      const fa = this.SearchFormArea;
      if (fa.GetInvalidForms().length !== 0) {
        fa.ValidateForm(fa.FORM_GROUP);
        return;
      }
    }
    // get the data
    let data: any = [];
    if (this.SelectedFormGroups.length > 0) {
      this.SelectedFormGroups.forEach(fg => data.push(fg.getRawValue()));
    } else {
      data = this.Form.getRawValue();
    }
    if (button.inlineBody != null) {
      const props = Object.keys(button.inlineBody);
      props.forEach(prop => {
        data[prop] = button.inlineBody[prop];
      });
    }
    const args = new OperationButtonEventArgs();
    if (this.CustomForm) {
      const d = { Form: {}, Array: [] };
      d.Form = this.SearchFormArea.FORM_GROUP.getRawValue();
      d.Array = data;
      args.Data = d;
    } else {
      args.Data = data;
    }
    args.Url = button.url;
    args.HttpMethod = button.method;
    args.Navigate = button.navigate;
    args.Form = this.Group;
    args.RouteType = button.routeType;
    args.ButtonOperation = button.buttonOps;
    args.IsSingleSelection = this.SelectionMode === 1;
    // return SelectedRows data
    if (button.editor == null) {
      this.OperationExecuted.emit(args);
    } else {
      this.dialog.open(PopupViewComponent, {
        width: '320px',
        data: new PopupDataArgs(null, null, button.label, PopupType.Form,
          new FormList(button.editor), this.ButtonOperationContinue,
          { fwd: args, context: this })
      });
    }
  }

  private ButtonOperationContinue(data, param, formgroup: FormGroup, dialog: MatDialogRef<PopupViewComponent>) {
    const args: OperationButtonEventArgs = param.fwd;
    const keys = Object.keys(data);
    args.Data.forEach(d => {
      keys.forEach(k => {
        d[k] = data[k];
      });
    });
    const that: TableViewComponent = param.context;
    that.OperationExecuted.emit(args);
    dialog.close();
  }

  private GetKeyValueParamsForSelectedItem(item: any) {
    const keys = Object.keys(item);
    const data: KeyValuePair[] = [];
    keys.forEach(k => {
      data.push(new KeyValuePair(k, item[k]));
    });
    return data;
  }

  RowSelected(event: SelectionEventArgs, row) {
    if (this.SelectionMode === 1) {
      if (event.EventArgs instanceof MatRadioChange) {
        this.SelectedFormGroups = [];
        this.SelectedFormGroups.push(event.Sender.form);
      }
    } else if (this.SelectionMode > 1) {
      if (event.Value === true) {
        this.SelectedFormGroups.push(event.Sender.form);
      } else {
        const index = this.SelectedFormGroups.indexOf(event.Sender.form, 0);
        if (index >= 0) {
          this.SelectedFormGroups.splice(index, 1);
        }
      }
    }
    const formCtrls = Object.keys(event.Sender.form.controls);
    formCtrls.forEach(ctrls => {
      const model = this.GetFormEditor(row, ctrls);
      if (model.visibilityDependent && model.parentKey === event.Sender.formEditorModel.key) {
        let hide: boolean;
        switch (model.visibilityOperator) {
          case VisibilityOperators.EQUALS:
            hide = !(event.Value === model.visibleOnParentValue);
            break;
          case VisibilityOperators.NOT_EQUALS:
            hide = !(event.Value !== model.visibleOnParentValue);
            break;
          case VisibilityOperators.GREATER_THAN:
            hide = !(event.Value > model.visibleOnParentValue);
            break;
          case VisibilityOperators.GREATER_THAN_EQUALS:
            hide = !(event.Value >= model.visibleOnParentValue);
            break;
          case VisibilityOperators.LESS_THAN:
            hide = !(event.Value < model.visibleOnParentValue);
            break;
          case VisibilityOperators.LESS_THAN_EQUALS:
            hide = !(event.Value <= model.visibleOnParentValue);
            break;
          default:
            break;
        }
        model.hidden = hide;
        const fc = event.Sender.form.get(model.key);
        if (hide) {
          fc.disable();
        } else {
          fc.enable();
        }
      }
    });
  }

  // get style for row
  GetStyle(row) {
    if (row && row.formatter) {
      const style = new Formatter(row.formatter).styleObj;
      return style;
    }
  }

  // get style for individual cell
  GetCellStyle(row, key) {
    const format = this.GetValue(row, key).formatter;
    if (format != null) {
      return new Formatter(format).styleObj;
    }
  }

  IsSelected(key: string, value: any) {
    return this.SelectedFormGroups.some(t => t.get(key).value === value);
  }

  private UpdateDataSource() {
    this.LoadColumnInfo();
    this.LoadTableData();
    if (this.PageSize) {
      if (!this.PageSizeOptions.some(t => t === this.PageSize)) {
        this.PageSizeOptions.push(this.PageSize);
      }
      this.PageSizeLocal = this.PageSize;
    }
    if (this.Operations) {
      if (typeof this.Operations === 'string') {
        this.Operations = JSON.parse(this.Operations);
      }
    }
    if (this.Legends) {
      if (typeof this.Legends === 'string') {
        this.Legends = JSON.parse(this.Legends);
      }
    }
  }

  public GetValue(row, name, defVal?) {
    if (defVal === undefined) {
      defVal = '';
    }
    name = name.split('.');
    for (let i = 0; i < name.length; i++) {
      if (row[name[i]] === undefined) {
        return defVal;
      }
      row = row[name[i]];
    }
    if (row === null) {
      return '';
    }
    return row;
  }

  public GetFormEditor(row, name): FormEditorModel {
    const form = this.GetValue(row, name).formEditor;
    const fem = new FormEditorModel(form, true);
    return fem;
  }

  private LoadTableData() {
    if (this.TABLE_DATA) {
      if (!this.RemoteOperations && this.DataSource) {
        this.DataSource.paginator = this.paginator;
        this.DataSource.sort = this.sort;
        this.DataSource.filterPredicate = this.CustomFilter;
      }

      let rowindex = 0;
      this.Form = this.fb.array([]);
      this.Group = new FormGroup({ Rose: this.Form });
      const formOps = new CoreFormOps(this.fb, this.remote, false);
      if (typeof this.TABLE_DATA === 'string') {
        this.TABLE_DATA = JSON.parse(this.TABLE_DATA);
      }
      this.TABLE_DATA.forEach(row => {
        const cells = Object.keys(row);
        const formEditorModels: FormEditorModel[] = [];
        cells.forEach(cell => {
          const val = this.GetValue(row, cell);
          if (val.formEditor) {
            formEditorModels.push(new FormEditorModel(val.formEditor));
          }
        });
        const group = formOps.GetTheGroup(formEditorModels);
        this.Form.controls.push(group);
        rowindex++;
      });
      this.DataSource = new MatTableDataSource(this.TABLE_DATA);

      if (this.RemoteOperations && this.sort) {
        this.sort.sortChange.observers.splice(0, this.sort.sortChange.observers.length);
        this.sort.sortChange.subscribe(() => {
          this.paginator.pageIndex = 0;
          this.EmitSortPageChange();
        });
        this.paginator.page.observers.splice(0, this.paginator.page.observers.length);
        this.paginator.page.subscribe(() => this.EmitSortPageChange());
      } else {
        this.DataSource.paginator = this.paginator;
        this.DataSource.sort = this.sort;
        this.DataSource.filterPredicate = this.CustomFilter;
      }
      if (this.RemoteOperations && this.paginator) {
        this.paginator.length = this.TotalRowCount;
      }
    } else {
      this.DataSource = null;
    }
  }
  private LoadColumnInfo() {
    if (this.Columns != null && this.Columns !== undefined) {
      if (typeof this.Columns === 'string') {
        this.Columns = JSON.parse(this.Columns);
      }
      const keys = Object.keys(this.Columns);
      this.ColumnHeaders = [];
      this.ShownColumns = [];
      TableViewComponent.Columns_static = [];
      keys.forEach(t => {
        const obj: any = {};
        obj.key = t;
        obj.value = this.Columns[t];
        this.ColumnHeaders.push(obj);
        this.ShownColumns.push(t);
        TableViewComponent.Columns_static.push(t);
      });
    }
    if (this.ShownColumns.length > 0) {
      if (this.ShowStatusToggle) {
        this.ShownColumns.push('status');
      }
      if (this.ShowDeleteButton) {
        this.ShownColumns.push('restore');
      }
      if (this.ShowDeleteButton || this.ShowEditButton || this.TimeLineUrl != null) {
        this.ShownColumns.push('action');
      }
    }
    this.CheckSelectionMode();
  }

  private CheckSelectionMode() {
    this.SelectionMode = 0;
    if (this.TABLE_DATA && this.TABLE_DATA.length > 0) {
      loop1:
      for (let index = 0; index < this.TABLE_DATA.length; index++) {
        const element = this.TABLE_DATA[index];
        const col = Object.keys(element);
        for (let i = 0; i < col.length; i++) {
          const cell = element[col[i]];
          if (cell.formEditor != null && cell.formEditor.autoFocus === true) {
            if (cell.formEditor.formEditorType === 'Radio') {
              this.SelectionMode = 1;
              break loop1;
            } else if (cell.formEditor.formEditorType === 'CheckBox') {
              this.SelectionMode = 2;
              break loop1;
            } else {
              this.SelectionMode = 0;
            }
          }
        }
      }
    }
  }

  ExecuteCreate() {
    this.CreateClicked.emit();
  }

  ToggleSearch() {
    this.ShowFilter = !this.ShowFilter;
  }

  public IsButtonDisabled() {
    // is selection available
    const v1 = this.SelectionMode > 0;
    // is either nothing selected or selected are invalid
    const v2 = this.SelectedFormGroups.length === 0 || this.SelectedFormGroups.some(t => t.invalid);
    // is selection not available
    const v3 = this.SelectionMode === 0;
    // are all groups invalid
    const array: FormArray = <FormArray>this.Group.controls['Rose'];

    const v4 = array.controls.some(t => t.invalid);

    return (v1 && v2) || (v3 && v4);
  }

  RefreshData() {
    this.ReloadData.emit();
  }

  ShowTimeline(row) {
    let id = row.id;
    if (row.id.formEditor != null) {
      id = row.id.formEditor.initialValue;
    }
    const obj = { 'id': id };
    const param = new HttpParams({ fromObject: obj });
    this.remote.get(this.TimeLineUrl, param).subscribe(
      data => {
        const dd = [];
        data.forEach(d => {
          dd.push(new TimeLineModel(d));
        });
        this.dialog.open(TimeLineViewComponent, { width: '600px', data: dd });
      },
      error => {
        this.remote.logInformationToConsole(true, true, 'Error while getting timelines', error);
        this.snack.open('Could not get data from server', null, { duration: 2000 });
      }
    );
  }
}
