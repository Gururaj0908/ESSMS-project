import { FormList } from './FormList';
export class SubForm {
   // name of group/array
   public groupOrArrayName: string;
   // editors of group/array
   public formLists: FormList[];
   // is array?
   public isArray: boolean;
   // will provide add button if true
   public isDynamic: boolean;
   constructor(sf?: SubForm) {
      if (sf) {
         this.groupOrArrayName = sf.groupOrArrayName;
         this.isArray = sf.isArray;
         this.isDynamic = sf.isDynamic;
         if (sf.formLists) {
            if (this.formLists == null) {
               this.formLists = [];
            }
            sf.formLists.forEach(fl => {
               this.formLists.push(new FormList(fl));
            });
         }
      }
   }
}
