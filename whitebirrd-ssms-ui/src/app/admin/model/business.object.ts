import { RouteType } from './RouteType';

export class BusinessObject {
  id: number;
  objectName: string;
  displayTag: string;
  url: string;
  menuAction: string;
  objectLevel: number;
  displayOrder: number;
  isHidden: boolean;
  children: BusinessObject[];
  parentId: number;
  icon: string;
  routeType: RouteType;

  constructor(b?: BusinessObject) {
    this.children = [];
    if (b != null) {
      this.id = b.id;
      this.objectName = b.objectName;
      this.displayTag = b.displayTag;
      this.url = b.url;
      this.menuAction = b.menuAction;
      this.objectLevel = b.objectLevel;
      this.displayOrder = b.displayOrder;
      this.isHidden = b.isHidden;
      this.parentId = b.parentId;
      this.icon = b.icon;
      if (b.routeType != null) {
        if (typeof b.routeType === 'string') {
          this.routeType = (<any>RouteType)[b.routeType];
        } else {
          this.routeType = b.routeType;
        }
      } else {
        this.routeType = RouteType.List;
      }
      if (b.children != null) {
        b.children.forEach(c => {
          this.children.push(new BusinessObject(c));
        });
      }
    }

  }
}
