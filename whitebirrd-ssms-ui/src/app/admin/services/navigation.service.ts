import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';

@Injectable()
export class NavigationService {

  private subject = new Subject<any>();

  constructor() {
  }

  getSideMenuData(): Observable<any> {
    return this.subject.asObservable();
  }

  setSideMenuData(data: any) {
    this.subject.next(data);
  }

}
