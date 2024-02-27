import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse, HttpRequest, HttpResponse } from '@angular/common/http';
import { GlobalService } from './global.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { tap } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';
import { environment } from '../../environments/environment';
import { Constants, ValidationError} from '../constants';
import { LoginTimerService } from './login-timer.service';


@Injectable()
export class RemoteService {

  private static _ShowLoader: boolean;
  public static get ShowLoader(): boolean {
    return this._ShowLoader;
  }
  public static set ShowLoader(v: boolean) {
    this._ShowLoader = v;
  }


  private baseUrl: string;
  RetryCount = 0;

  constructor(private httpClient: HttpClient,
    private globalService: GlobalService,
    private route: Router,
    private loginTimer: LoginTimerService,
    private snb: MatSnackBar) {
    this.baseUrl = globalService.API_BASE_URL;
  }

  public GetFormData(formGroup: FormGroup): FormData {
    let option: FormData = null;
    if (formGroup.contains('_#$%^%$#_')) {
      option = new FormData();
      const val = formGroup.value;
      const keys = Object.keys(val);
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i];
        if (key !== '_#$%^%$#_') {
          const value = val[key];
          if (Array.isArray(value)) {
            for (let j = 0; j < value.length; j++) {
              const vals = value[j];
              if (vals instanceof File) {
                option.append(key, vals, vals.name);
              } else {
                option.append(key, vals);
              }
            }
          } else {
            option.append(key, value);
          }
        }
      }
    }
    return option;
  }

  get(path: string, param?: HttpParams, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    return this.httpClient.get<any>(this.baseUrl + path, { params: param }).pipe(
      tap( // Log the result or error
        returnData => this.logInformationToConsole(false, false, 'Received data', returnData),
        error => this.handleError(error)
      )
    );
  }

  async getAsync(path: string, param?: HttpParams, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    const request = new HttpRequest('GET', this.baseUrl + path, { params: param });
    const response = await this.httpClient.request(request).toPromise();
    RemoteService.ShowLoader = false;
    return <HttpResponse<any>>response;
  }

  deleteWithParam(path: string, param?: HttpParams, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    return this.httpClient.delete<boolean>(this.baseUrl + path, { params: param }).pipe(
      tap( // Log the result or error
        data => this.logInformationToConsole(false, false, 'Received data for delete', data),
        error => this.handleError(error)
      )
    );
  }

  post(url: string, data: any, form?: FormGroup, preferData?: boolean, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    let formdata: FormData = null;
    if (form) {
      formdata = this.GetFormData(form);
    }
    if (preferData && data) {
      formdata = null;
    }
    return this.httpClient.post<any>(this.baseUrl + url, formdata == null ? data : formdata).pipe(
      tap( // Log the result or error
        returnData => this.logInformationToConsole(false, false, 'Received data for post', returnData),
        error => this.handleError(error, form)
      ),
    );
  }

  put(url: string, data: any, form?: FormGroup, preferData?: boolean, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    let formdata: FormData = null;
    if (form) {
      formdata = this.GetFormData(form);
    }
    if (preferData && data) {
      formdata = null;
    }
    return this.httpClient.put<any>(this.baseUrl + url, formdata == null ? data : formdata).pipe(
      tap( // Log the result or error
        returnedData => this.logInformationToConsole(false, false, 'Received data for put/edit', returnedData),
        error => this.handleError(error, form)
      )
    );
  }

  delete(url: string, ids: any[], hardDelete?: boolean, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    let params = new HttpParams();
    if (ids) {
      ids.forEach(id => params = params.append('id', id));
    }
    if (hardDelete) {
      params = params.append('hardDelete', 'true');
    }
    return this.httpClient.delete<boolean>(this.baseUrl + url, { params: params }).pipe(
      tap( // Log the result or error
        data => this.logInformationToConsole(false, false, 'Received data for delete', data),
        error => this.handleError(error)
      )
    );
  }

  deleteWithOptions(url: string, options: any, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    return this.httpClient.delete<boolean>(this.baseUrl + url, options).pipe(
      tap( // Log the result or error
        data => this.logInformationToConsole(false, false, 'Received data for delete', data),
        error => this.handleError(error)
      )
    );
  }

  patch(url: string, data?: any, form?: FormGroup, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    let formdata: FormData = null;
    if (form) {
      formdata = this.GetFormData(form);
    }
    return this.httpClient.patch<any>(this.baseUrl + url, formdata == null ? data : formdata).pipe(
      tap( // Log the result or error
        receivedData => this.logInformationToConsole(false, false, 'Received data for patch', receivedData),
        error => this.handleError(error, form)
      )
    );
  }

  execute(method: string, url: string, data?: any, param?: HttpParams, form?: FormGroup, download?: boolean, hideLoader?: boolean) {
    if (hideLoader !== true) {
      RemoteService.ShowLoader = true;
    }
    return this.httpClient.request(method, this.baseUrl + url,
      { body: data, params: param, responseType: download ? 'blob' : 'json', observe: 'response' }).pipe(
        tap(
          d => this.logInformationToConsole(false, false, `Executed ${method}. got: `, d),
          e => this.handleError(e, form)
        )
      );
  }

  logInformationToConsole(always: boolean, error: boolean, message: any, ...params: any[]) {
    if (always || !environment.production) {
      error ? console.error(message, params ? params : '') : console.log(message, params ? params : '');
    }
    RemoteService.ShowLoader = false;
  }

  private handleError(error: HttpErrorResponse, form?: FormGroup) {
    RemoteService.ShowLoader = false;
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else if (error.status === 401) {
      if (this.RetryCount < 1) {
        // this.Relogin();
        this.RetryCount++;
        sessionStorage.clear();
        if (this.globalService.TENANT_ID === 'bms') {
          this.route.navigate(['login-form']);
        } else {
          this.route.navigate(['ecommerce/home']);
        }
      }
    } else if (error.status === 406) {
      try {
        if (form) {
          const errors: ValidationError[] = error.error;
          if (errors) {
            errors.forEach(err => {
              let fld = err.field;
              fld = fld.replace('[', '.').replace('].', '.');
              const ctrl = form.get(fld);
              ctrl.setErrors({ 'remote': err.errorMsg });
            });
          }
        } else {
          console.error(
            `Validation Failed`, error);
        }
      } catch (error) {
        this.snb.open('An Error occurred. Please try again later.', null,
          { duration: 2000, horizontalPosition: 'right', verticalPosition: 'top' });
        console.error('An error occurred', error);
      }
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      if (error.status === 500) {
        this.snb.open('An Error occurred. Please try again later.', null,
          { duration: 2000, horizontalPosition: 'right', verticalPosition: 'top' });
        console.error('An error occurred', error);
      } else if (error.status === 403) {
        this.snb.open('Requested resource is forbidden for you', null,
          { duration: 2000, horizontalPosition: 'right', verticalPosition: 'top' });
      }
      if (error.error) {
        console.error(`Backend returned ${error.error.error} (${error.error.status}), message was: ${error.error.message}`, error);
      } else {
        console.error('An Unknown error occurred', error);
      }

    }
  }

  // public Relogin() {
  //   this.RetryCount++;
  //   const userdata = sessionStorage.getItem(Constants.LOGGED_USER);
  //   if (userdata) {
  //     const user = JSON.parse(userdata);
  //     const refreshtoken = user.loginSuccess.refresh_token;
  //     this.globalService.INJECT_ACCESS_TOKEN = false;
  //     this.post('/essms-auth/authentication/refresh/accesstoken', { 'refresh_token': refreshtoken }).subscribe(
  //       data => {
  //         this.globalService.INJECT_ACCESS_TOKEN = true;
  //         this.RetryCount = 0;
  //         sessionStorage.setItem(Constants.LOGGED_USER, JSON.stringify(data));
  //         this.loginTimer.StartTimer(data.loginSuccess.expires_in, data.loginSuccess.refresh_token);
  //       },
  //       () => {
  //         if (this.globalService.TENANT_ID === 'bms') {
  //           this.route.navigate(['login-form']);
  //         } else {
  //           this.route.navigate(['ecommerce/home']);
  //         }
  //       }
  //     );
  //   }
  // }
}
