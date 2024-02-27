import { Component, OnInit } from '@angular/core';
import { FormList } from '../dynamic-form/FormList';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { BusinessObject } from '../model/business.object';
import { RouteType } from '../model/RouteType';
import { Constants } from '../../constants';
import { RemoteService } from '../../services/remote.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BusinessRouterService } from '../services/business-router.service';
import { DynamicButtonClickEventArgs } from '../mbo/form-area/DynamicButtonClickEventArgs';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-admin-form',
  templateUrl: './admin-form.component.html',
  styleUrls: ['./admin-form.component.scss']
})
export class AdminFormComponent implements OnInit {

  formList: FormList;
  prefilledValue: any;
  cancelText = this.l.GetString('Cancel');
  private isSelfSufficient: boolean;
  private bo: BusinessObject;
  resetDone = true;

  constructor(public l: LanguageService,
    private route: ActivatedRoute,
    private router: Router,
    private brs: BusinessRouterService,
    private snack: MatSnackBar,
    private remote: RemoteService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      if (params['id'] != null) {
        this.bo = this.brs.GetMenuItem(Number(params['id']));
      } else {
        this.bo = JSON.parse(sessionStorage.getItem(Constants.SELECTED_MENU));
      }
      if (this.bo) {
        this.brs.MenuItemSelector(this.bo);
        if (this.bo.routeType === RouteType.Form) {
          this.isSelfSufficient = true;
          this.formList = null;
          this.cancelText = this.l.GetString('Reset');
          const formurl = this.bo.menuAction;
          this.remote.get(formurl).subscribe(data => {
            this.formList = new FormList(data);
            sessionStorage.setItem(Constants.FORM_EDITOR, JSON.stringify(this.formList));
            sessionStorage.setItem(Constants.CREATE_URL, this.formList.url);
          }, error => {
            console.error(error);
          });
        } else {
          this.isSelfSufficient = false;
          const list = JSON.parse(sessionStorage.getItem(Constants.FORM_EDITOR));
          this.formList = new FormList(list);
          this.prefilledValue = JSON.parse(sessionStorage.getItem(Constants.ENTITY_FORM_DATA));
        }
      } else {
        this.snack.open(this.l.GetString('RequestFail'), null, { duration: 3000, verticalPosition: 'top' });
      }
    });
  }


  submit(event: FormGroup) {
    const method = this.formList.method.toLowerCase();
    // we need id to distinguish if its a new data for edit data for the same form
    if (event.value.id) {
      const url = this.formList.url;
      if (method === 'patch') {
        this.remote.patch(url ? url : sessionStorage.getItem(Constants.UPDATE_URL), event.getRawValue(), event).subscribe(
          data => {
            this.CallAfterPost(data, event);
          },
          error => {
            console.error(error);
          }
        );
      } else {
        this.remote.put(url ? url : sessionStorage.getItem(Constants.UPDATE_URL), event.getRawValue(), event).subscribe(
          data => {
            this.CallAfterPost(data, event);
          },
          error => {
            console.error(error);
          }
        );
      }
    } else {
      const url = this.formList.url;
      this.remote.post(url ? url : sessionStorage.getItem(Constants.CREATE_URL), event.getRawValue(), event).subscribe(
        data => {
          this.CallAfterPost(data, event);
        },
        error => {
          console.error(error);
        }
      );
    }
  }

  private CallAfterPost(data: BusinessObject, event: FormGroup) {
    if (data && data.objectName != null) {
      const d = new BusinessObject(data);
      this.brs.HandleNavigation(d, true);
    } else {
      if (this.isSelfSufficient) {
        this.snack.open(this.l.GetString('Saved'), null, { duration: 2000 });
      }
      this.cancel(event, this);
    }
  }

  public DynamicButtonExecuted(args: DynamicButtonClickEventArgs) {
    const resp = args.Data;
    const contentType = resp.headers.get('Content-Type');
    if (!contentType.includes('json')) {
      const file = new Blob([resp.body], { type: contentType });
      const url = URL.createObjectURL(file);
      const a = document.createElement('a');
      // a.download = desp;
      a.href = url;
      a.click();
    } else {
      const data = resp.body;
      switch (args.Button.routeType) {
        case RouteType.List:
          sessionStorage.setItem(Constants.NON_URL_TABLE_DATA, JSON.stringify(data));
          this.router.navigate(['admin/details']);
          break;
        case RouteType.Tree:
          break;
        case RouteType.UI:
          this.CallAfterPost(data, null);
          break;
        case RouteType.Form:
        default:
          if (data.objectName) {
            const bo = new BusinessObject(data);
            this.brs.HandleNavigation(bo, true, true);
          } else {
            this.resetDone = false;
            this.formList = new FormList(data);
            this.resetDone = true;
          }
          break;
      }
    }
  }


  cancel(fg: FormGroup, context?: any) {
    if (this.isSelfSufficient) {
      if (this.formList.isReset) {
        fg.reset();
      }
    } else {
      if (!context) {
        context = this;
      }
      context.router
        .navigate(['admin/details/' + (JSON.parse(sessionStorage.getItem(Constants.SELECTED_MENU)) as BusinessObject).id]);
    }
  }


}
