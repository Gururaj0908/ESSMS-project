import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GlobalService } from '../services/global.service';
import { Constants } from '../constants';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private globalService: GlobalService) {

  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    let lang = localStorage.getItem(Constants.SELECTED_LANGUAGE_CODE);
    if (lang == null) {
      lang = 'en_in';
    }
    const currentUser = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
    if (currentUser && currentUser.loginSuccess.access_token && this.globalService.INJECT_ACCESS_TOKEN) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.loginSuccess.access_token}`,
          'X-TENANT-ID': this.globalService.TENANT_ID,
          'Accept-Language': lang,
          userGUID: `${currentUser.loginSuccess.userGUID}`,
          branchGUID: `${currentUser.loginSuccess.branchGUID}`
        }
      });
    } else {
      request = request.clone({
        setHeaders: {
          'X-TENANT-ID': this.globalService.TENANT_ID,
          'Accept-Language': lang
        }
      });
    }
    return next.handle(request);
  }
}
