import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';
import { RemoteService } from '../../services/remote.service';
import { GlobalService } from '../../services/global.service';
import { AuthService } from '../../services/auth.service';
import { Constants } from '../../constants';
import { BusinessRouterService } from '../services/business-router.service';
import { LanguageService } from '../../services/language.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Output()
  toggleSidenav = new EventEmitter<string>();

  @Input() IsMobile: boolean;




  branchName: string;


  constructor(
    public l: LanguageService,
    private router: Router,
    public gs: GlobalService,
    public brs: BusinessRouterService,
    private remoteService: RemoteService,
    private authService: AuthService) {
  }


  ngOnInit() {
    this.gs.LanguageCode = localStorage.getItem(Constants.SELECTED_LANGUAGE_CODE);
    if (this.brs.LoggedUser.loginSuccess.branchGUID != null) {
      this.remoteService.get('/essms-admin/branch/detail/' + this.brs.LoggedUser.loginSuccess.branchGUID).subscribe(
        branchData => {
          this.branchName = branchData.name;
        });
    }
  }


  /**
   * Called when the side toggler is clicked
   */
  onMenuTogglerClick() {
    this.toggleSidenav.emit('clicked');
  }

  /**
   * Called when the signout button is clicked
   */
  signOut() {
    this.authService.signOut();
    if (this.gs.TENANT_ID.toUpperCase() === 'BMS') {
      this.router.navigate(['login-form']);
    } else {
      this.router.navigate(['ecommerce/home']);
    }
  }

  IsBms(): boolean {
    return this.gs.TENANT_ID.toUpperCase() === 'BMS';
  }

  /**
   * Called when the index of the tab is changed either manually or autometic
   * @param index The index of the tab just changed
   */
  TabChanged(index: number) {
    //if (this.brs.SelectedPrimaryMenuIndex === index) {
    //  return;
    //}
    const topitem = this.brs.MainMenu[index];
    this.brs.MainMenuSelected(topitem);
  }

}
