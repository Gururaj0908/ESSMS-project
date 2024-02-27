import { Component, ViewChild, ChangeDetectorRef, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import { SideNavigationComponent } from './side-navigation/side-navigation.component';
import { MatSidenav } from '@angular/material/sidenav';
import { MediaMatcher } from '@angular/cdk/layout';
import { BusinessObject } from './model/business.object';
import { RemoteService } from '../services/remote.service';
import { HttpParams } from '@angular/common/http';
import { Constants } from '../constants';
import { MenuHelperService } from './services/menu.helper.service';
import { BusinessRouterService } from './services/business-router.service';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalService } from '../services/global.service';
import { LoginTimerService } from '../services/login-timer.service';

@Component({
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnDestroy, OnInit {

  title = 'admin';
  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;
  Remote = RemoteService;
  SelectedTopMenu: BusinessObject;

  @ViewChild(SideNavigationComponent)
  private sideNavigationComponent: SideNavigationComponent;

  @ViewChild(MatSidenav)
  private matSideNav: MatSidenav;


  public SelectedSideMenu: BusinessObject;
  public SelectedSideItem: BusinessObject;


  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    public remote: RemoteService,
    private router: Router,
    public gs: GlobalService,
    public brs: BusinessRouterService,
    private refresh: LoginTimerService
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');

    this._mobileQueryListener = () => {
      changeDetectorRef.detectChanges();
      if (this.mobileQuery.matches) {
        this.matSideNav.close();
      } else {
        this.matSideNav.open();
      }
    };
    this.mobileQuery.addListener(this._mobileQueryListener);
  }


  async ngOnInit() {
    if (!this.refresh.Running) {
      this.refresh.StartTimer(true);
    }
    const user = sessionStorage.getItem(Constants.LOGGED_USER);
    if (user == null) {
      const return_url = this.router.url;
      if (this.gs.TENANT_ID === 'bms') {
        this.router.navigate(['login-form'], { queryParams: { 'returnUrl': return_url } });
      } else {
        this.router.navigate(['login'], { queryParams: { 'returnUrl': return_url } });
      }
    } else {
      await this.brs.FetchBusinessObjects();
    }
    // load the users
    const userparam = { 'roleGUIDs': 'c13c54d4-f23b-4c98-a343-26050ba49ad2' };
    this.remote.get('/essms-auth/option/userbyroles', new HttpParams({ fromObject: userparam })).subscribe(
      d => { sessionStorage.setItem(Constants.APPLICATION_USERS, JSON.stringify(d)); },
      e => { this.remote.logInformationToConsole(true, true, 'Error While Loading Users', e); }
    );
  }



  public SetSelectedSideItem(v: BusinessObject) {
    this.SelectedSideItem = v;
  }

  public SetSelectedSideMenu(v: BusinessObject) {
    this.SelectedSideMenu = v;
  }


  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  tabChanged(event) {
    if (!this.matSideNav.opened) {
      // this.matSideNav.open();
    }
  }

  toggleSidenav(event) {
    this.matSideNav.toggle();
  }

  public GetNavMode() {
    const navMode = this.mobileQuery.matches ? 'over' : 'side';
    return navMode;
  }

  public ItemSelectedSideNav() {
    if (this.mobileQuery.matches) {
      this.matSideNav.close();
    }
  }


}
