import { AdditionalPropertyName } from './AdditionalPropertyName';
export class AdditionalProperty {
   id: number;
   textValue: string;
   decimalValue: number;
   dateValue: Date;
   bitValue: boolean;
   propertyNameId: string;
   propertyInfo: AdditionalPropertyName;

   constructor(d?: AdditionalProperty) {
      if (d != null) {
         this.id = d.id;
         this.textValue = d.textValue;
         this.decimalValue = d.decimalValue;
         this.bitValue = d.bitValue;
         if (d.dateValue) {
            this.dateValue = new Date(d.dateValue);
         }
         this.propertyNameId = d.propertyNameId;
         if (d.propertyInfo != null) {
            this.propertyInfo = new AdditionalPropertyName(d.propertyInfo);
         }
      }
   }
}
