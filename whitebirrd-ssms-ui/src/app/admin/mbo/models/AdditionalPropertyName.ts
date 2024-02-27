import { BaseModel } from './BaseModel';
import { AdditionalPropertyType } from '../enum/AdditionalPropertyType';
import { ProjectStage } from '../enum/ProjectStage';

export class AdditionalPropertyName extends BaseModel {


   public name: string;
   public type: AdditionalPropertyType;
   public required: boolean;
   public optionalValues: string;
   public index: number;
   public forStage: ProjectStage;

   constructor(d?: AdditionalPropertyName) {
      super(d);
      if (d != null) {
         this.name = d.name;
         this.type = d.type;
         this.required = d.required;
         this.optionalValues = d.optionalValues;
         this.index = d.index;
         if (d.forStage != null) {
            if (typeof d.forStage === 'string') {
               this.forStage = (<any>ProjectStage)[d.forStage];
            } else {
               this.forStage = d.forStage;
            }
         }
      }
   }

   public override toString = (): string => {
      return this.name;
   }
}
