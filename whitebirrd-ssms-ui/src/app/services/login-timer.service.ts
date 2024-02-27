import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from '../constants';
import { HttpClient } from '@angular/common/http';
import { GlobalService } from './global.service';

@Injectable({
  providedIn: 'root'
})
export class LoginTimerService {

  public Running: boolean;
  constructor(private router: Router, private http: HttpClient, private gs: GlobalService) {
    this.Running = false;
  }

  public StartTimer(refreshNow = false) {
    const UserString = sessionStorage.getItem(Constants.LOGGED_USER);
    const UserDetails = JSON.parse(UserString);
    const token = UserDetails.loginSuccess.refresh_token;
    const expiresIn = UserDetails.loginSuccess.expires_in;
    this.Running = true;
    if (refreshNow) {
      this.RefreshFunction(token);
    }
    this.RunRefresh(expiresIn, token);
  }

  private RunRefresh(expiresIn: number, refresh_token: string) {
    const timeout = (expiresIn - 60) * 1000;
    setTimeout(() => this.RefreshFunction(refresh_token), timeout);
  }

  private RefreshFunction(token: string) {
    this.http.post<any>(this.gs.API_BASE_URL + '/essms-auth/authentication/refresh/accesstoken', { 'refresh_token': token })
      .subscribe(
        data => {
          console.log('Refresh Success', data);
          sessionStorage.setItem(Constants.LOGGED_USER, JSON.stringify(data));
          this.RunRefresh(data.loginSuccess.expires_in, data.loginSuccess.refresh_token);
        },
        error => {
          console.error('Refresh Error in timer', error);
          this.router.navigate(['ecommerce/home']);
        }
      );
  }
}
