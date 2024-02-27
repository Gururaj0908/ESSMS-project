import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { GlobalService } from '../../services/global.service';
import { RemoteService } from '../../services/remote.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivateDeactivate } from '../model/activate.deactivate';
import { Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';

@Injectable()
export class CrudService {

  tableData: any;
  displayableColumns: any;
  displayedHeaders: any;
  displayableHeader: any;

  constructor(private http: HttpClient,
    private remoteService: RemoteService,
    private globalService: GlobalService,
    private matSnackBar: MatSnackBar) { }

  getList(url: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(this.globalService.API_BASE_URL + url, { observe: 'response' });
  }

  getListIncludingInactiveRecords(url: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(this.globalService.API_BASE_URL + url + '?includeDeActive=true', { observe: 'response' });
  }

  setTableData(tableData) {
    this.tableData = tableData;
  }

  create(url: string, entity: any, callback?: (context) => void, cb_param?: any, form?: FormGroup) {
    const test = true;
    this.remoteService.post(url, entity, form).subscribe(
      data => {
        if (this.tableData.data) {
          this.tableData.data.unshift(data);
          this.tableData._updateChangeSubscription();
        } else {
          this.tableData.unshift(data);
        }
        callback(cb_param);
      },
      error => {
        this.remoteService.logInformationToConsole(true, true, error);
      });
  }

  put(url: string, entity: any, callback?: (context) => void, cb_param?: any, form?: FormGroup) {
    this.remoteService.put(url, entity, form).subscribe(
      data => {
        let oldLoc = -1;
        if (this.tableData.data) {
          oldLoc = this.tableData.data.findIndex(t => t.id === entity.id);
        } else {
          oldLoc = this.tableData.findIndex(t => t.id === entity.id);
        }
        if (oldLoc > -1) {
          if (this.tableData.data) {
            this.tableData.data.splice(oldLoc, 1, data);
            this.tableData._updateChangeSubscription();
          } else {
            this.tableData.splice(oldLoc, 1, data);
          }
        }
        callback(cb_param);
      },
      error => {
        this.remoteService.logInformationToConsole(true, true, error);
      }
    );
  }

  activateDeactivate(url: string, id: object, status: boolean) {
    const activateDeactivate = new ActivateDeactivate();
    activateDeactivate.id = id;
    activateDeactivate.status = status;
    this.remoteService.patch(url, activateDeactivate).subscribe(
      data => {
        const oldLoc = this.tableData.findIndex(t => t.id === id);
        if (oldLoc > -1) {
          if (this.tableData.data) {
            this.tableData.data.splice(oldLoc, 1, data);
            this.tableData._updateChangeSubscription();
          } else {
            this.tableData.splice(oldLoc, 1, data);
          }
        }
      },
      error => {
      }
    );
  }

  restore(url: string, id: object, restore: boolean) {
    const restoreModel = {
      'id': id,
      'restore': restore
    };
    this.remoteService.patch(url, restoreModel).subscribe(
      data => {
        const oldLoc = this.tableData.findIndex(t => t.id === id);
        if (oldLoc > -1) {
          if (this.tableData.data) {
            this.tableData.data.splice(oldLoc, 1, data);
            this.tableData._updateChangeSubscription();
          } else {
            this.tableData.splice(oldLoc, 1, data);
          }
        }
      },
      error => {
      }
    );
  }

  delete(deleteUrl: string, restoreUrl: string, id: number, callback: (param) => void, cb_param: any) {
    this.trashData(deleteUrl, restoreUrl, [id], callback, cb_param);
  }

  trashData(deleteUrl: string, restoreUrl: string, ids: number[], callback: (param) => void, cb_param: any) {
    let oldLoc: number;
    const dataMap: Map<number, any> = new Map();
    let undoSelected = false;
    this.remoteService.delete(deleteUrl, ids, ids.length > 1).subscribe(
      data => {
        // remove items
        for (const id of ids) {
          oldLoc = -1;
          if (this.tableData.data) {
            oldLoc = this.tableData.data.findIndex(d => d.id === id);
          } else {
            oldLoc = this.tableData.findIndex(d => d.id === id);
          }
          if (oldLoc > -1) {
            dataMap.set(oldLoc, this.tableData[oldLoc]);
            if (this.tableData.data) {
              this.tableData.data.splice(oldLoc, 1);
              this.tableData._updateChangeSubscription();
            } else {
              this.tableData.splice(oldLoc, 1);
            }

          }
        }
        callback(cb_param);
        // show repent option if there was only one item deleted
        if (ids.length === 1) {
          const snackRef = this.matSnackBar.open('Item Deleted.', 'Undo', {
            duration: 8000
          });
          snackRef.onAction().subscribe(() => {
            undoSelected = true;
            snackRef.dismiss();
          });
          snackRef.afterDismissed().subscribe(() => {
            if (!undoSelected) {
              this.remoteService.delete(deleteUrl, ids, true).subscribe(
                success => { },
                error => {
                  this.remoteService.logInformationToConsole(true, true, error);
                }
              );
            } else {
              this.remoteService.patch(restoreUrl + '?id=' + ids[0]).subscribe(
                success => {
                  this.restoreData(dataMap);
                  cb_param.Table_Data = this.tableData;
                },
                error => {
                  this.remoteService.logInformationToConsole(true, true, error);
                }
              );
            }
          });
        }
      },
      error => {
        this.remoteService.logInformationToConsole(true, true, error);
      }
    );
  }

  restoreData(dataMap) {
    dataMap.forEach((value: any, key: number) => {
      if (this.tableData.data) {
        this.tableData.data.splice(key, 0, value);
        this.tableData._updateChangeSubscription();
      } else {
        this.tableData.splice(key, 0, value);
      }
    });
  }

}
