import { RouteType } from '../model/RouteType';
import { KeyValue } from '@angular/common';

export class DashboardItem {
  businessObjectId: number;
  detailUrl: string;
  menuAction: string;
  routeType: RouteType;
  totalCount: number;

  constructor(f?: DashboardItem) {
    if (f != null) {
      this.businessObjectId = f.businessObjectId;
      this.detailUrl = f.detailUrl;
      this.menuAction = f.menuAction;
      this.totalCount = f.totalCount;
      if (f.routeType != null) {
        if (typeof f.routeType === 'string') {
          this.routeType = (<any>RouteType)[f.routeType];
        } else {
          this.routeType = f.routeType;
        }
      }
    }
  }
}

export class KeyValuePair<K, V> implements KeyValue<K, V> {
  constructor(public key: K, public value: V) { }
}
