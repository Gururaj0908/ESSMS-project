import { Injectable } from '@angular/core';
import { Constants } from '../constants';
import { BehaviorSubject } from 'rxjs';
@Injectable()
export class AuthService {

  constructor() { }
  private signOutMessage = new BehaviorSubject('0');
  updateSignOut = this.signOutMessage.asObservable();

  updateSignOutData() {
    this.signOutMessage.next('true');
  }
  signOut() {
    sessionStorage.removeItem(Constants.LOGGED_USER);
    sessionStorage.removeItem(Constants.MENU_ITEMS);
    sessionStorage.removeItem(Constants.ENTITY_FORM_DATA);
    sessionStorage.removeItem(Constants.CREATE_URL);
    sessionStorage.removeItem(Constants.UPDATE_URL);
    sessionStorage.removeItem(Constants.FORM_EDITOR);
    sessionStorage.removeItem(Constants.SELECTED_MENU);
  }

}
