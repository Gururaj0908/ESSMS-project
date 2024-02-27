export abstract class BaseModel {
   public id: string;
   public active: boolean;
   public createdBy: string;
   public createdOn: Date;

   /**
    *
    */
   constructor(d?: BaseModel) {
      if (d) {
         this.id = d.id;
         this.active = d.active;
         this.createdBy = d.createdBy;
         if (d.createdOn != null) {
            this.createdOn = new Date(d.createdOn);
         }
      }
   }

   public toString = (): string => {
      return `Base Model (id: ${this.id})`;
   }
}
