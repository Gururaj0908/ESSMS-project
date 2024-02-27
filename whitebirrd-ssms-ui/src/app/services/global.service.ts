import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Injectable()
export class GlobalService {
  APP_NAME: string;
  TITLE = '';
  API_BASE_URL = '';
  public TENANT_ID: string;
  public INJECT_ACCESS_TOKEN = true;
  public REFRESH_TOKEN_TIME_INTERVAL = 30;
  public LanguageCode: string;

  constructor(public titleService: Title) {
    console.log('Tenant Hostname - ' + document.location.hostname);
    if (document.location.hostname.indexOf('demo.horolab') !== -1) {
      this.TENANT_ID = 'horolab';
      this.API_BASE_URL = 'http://essms.horolab.in';
    } else if (document.location.hostname.indexOf('qa.horolab') !== -1) {
      this.TENANT_ID = 'horolab';
      this.API_BASE_URL = 'http://qa.api.horolab.in';
    } else if (document.location.hostname.indexOf('horolab') !== -1) {
      this.TENANT_ID = 'horolab';
      this.API_BASE_URL = 'http://essms.horolab.in';
    } else if (document.location.hostname.indexOf('whitebirrd') !== -1) {
      this.TENANT_ID = 'bms';
      this.API_BASE_URL = 'http://essms.horolab.in';
    } else {
      // for gaurav: Please dont commit this to git
      //  this.API_BASE_URL = 'http://localhost:9102';
      // for anup :  Please dont commit this to git
      // this.API_BASE_URL = 'http://localhost:9102';
      // for remote
      this.API_BASE_URL = 'http://essms.horolab.in';
      //this.TENANT_ID = 'horolab';
      this.TENANT_ID = 'bms';
    }
    this.APP_NAME = this.TENANT_ID === 'bms' ? 'BMS' : 'eSSMS';
    this.LanguageCode="";
  }

  SetTitle(title: string) {
    this.TITLE = title;
    if (title) {
      this.titleService.setTitle(title + ' | ' + this.APP_NAME);
    } else {
      this.titleService.setTitle(this.APP_NAME);
    }
  }

  GetTitle(): string {
    return this.TITLE;
  }
}
