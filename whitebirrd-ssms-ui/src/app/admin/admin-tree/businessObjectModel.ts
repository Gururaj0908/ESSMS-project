import { Itreeable } from '../mbo/tree-view/Itreeable';

export class BusinessObjectModel implements Itreeable {
   parent: BusinessObjectModel;
   children: BusinessObjectModel[];
   id: any;
   selected: boolean;
   selectable: boolean;
   displayOrder: number;
   displayTag: string;
   isHidden: boolean;
   objectLevel: number;
   objectName: string;
   routeType: string;

   constructor(f?: BusinessObjectModel) {
      if (f != null) {
         this.id = f.id;
         this.displayOrder = f.displayOrder;
         this.displayTag = f.displayTag;
         this.isHidden = f.isHidden;
         this.objectLevel = f.objectLevel;
         this.objectName = f.objectName;
         this.routeType = f.routeType;
         this.selectable = f.selectable;
         this.selected = f.selected;
      }

   }
}
