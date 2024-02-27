import { Component, OnInit } from '@angular/core';
import { DashboardItem, KeyValuePair } from './DashboardItem';
import { KeyValue } from '@angular/common';
import { MenuHelperService } from '../services/menu.helper.service';
import { BusinessRouterService } from '../services/business-router.service';
import { Constants } from '../../constants';
import { BusinessObject } from '../model/business.object';
import { RemoteService } from '../../services/remote.service';
import { Router } from '@angular/router';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public Data: KeyValue<string, KeyValue<string, DashboardItem[]>[]>[];
  public Table: any;
  public Loading: boolean;


  constructor(private menuHelper: MenuHelperService,
    private br: BusinessRouterService,
    private remote: RemoteService,
    public l: LanguageService,
    private r: Router) {
  }

  async ngOnInit() {
    let businessObject = JSON.parse(sessionStorage.getItem(Constants.SELECTED_MENU)) as BusinessObject;
    if ((businessObject == null || businessObject.id !== 3) && this.r.url === '/admin/dashboard') {
      businessObject = this.br.GetMenuItem(3);
    }
    if (businessObject) {
      this.br.MenuItemSelector(businessObject);
      const url = businessObject.url;
      this.Loading = true;
      this.remote.get(url).subscribe(d => {
        if (d.categoryEventMap != null) {
          this.StructureData(d);
          this.structureTable(d);
        }
        this.Loading = false;
      }, error => {
        this.remote.logInformationToConsole(true, true, error);
        this.Loading = false;
      });
    }

  }

  private StructureData(d) {
    const category = Object.keys(d.categoryEventMap);
    this.Data = [];
    category.forEach(c => {
      const event = d.categoryEventMap[c];
      const keys = Object.keys(event);
      const item = [];
      keys.forEach(v => {
        const arr = event[v];
        const output = [];
        arr.forEach(ar => {
          output.push(new DashboardItem(ar));
        });
        item.push(new KeyValuePair<string, DashboardItem[]>(v, output));
        arr.forEach(ar => {
          if (ar.branchEventMap != null) {
            const k = Object.keys(ar.branchEventMap);
            k.forEach(key => {
              const in_output = [];
              const entry = new DashboardItem();
              entry.totalCount = ar.branchEventMap[key];
              in_output.push(entry);
              item.push(new KeyValuePair<string, DashboardItem[]>(key, in_output));
            });
          }
        });
      });
      this.Data.push(new KeyValuePair<string, KeyValue<string, DashboardItem[]>[]>(c, item));
    });
  }

  public GoTo(businessObjectId: number) {
    const bo = this.br.GetMenuItem(businessObjectId);
    this.br.HandleNavigation(bo, true, true);

  }

  public structureTable(d: { extraDetails: any; }) {
    this.Table = d.extraDetails;
  }


}
