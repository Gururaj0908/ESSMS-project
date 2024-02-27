// import { Injectable } from '@angular/core';

// @Injectable({
//   providedIn: 'root'
// })
// export class ProductDataServiceService {

//   constructor() { }
// }
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })
export class ProductDataServiceService {

  private messageSource = new BehaviorSubject('0');
  updateCart = this.messageSource.asObservable();

  constructor() { }

  updateCartData(cartdata: string) {
    this.messageSource.next(cartdata);
  }

}
