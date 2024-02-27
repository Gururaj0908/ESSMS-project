import { BaseModel } from './BaseModel';
import { TrackingStatus } from './RequirementModel';

export class IssuesModel extends BaseModel {

   public projectItem: string;
   public title: string;
   public description: string;
   public documents: IssueDocument[];
   public trackedId: string;
   public statusUpdatedOn: Date;
   public currentStatus: TrackingStatus;

   constructor(d?: IssuesModel) {
      super(d);
      if (d != null) {
         this.projectItem = d.projectItem;
         this.title = d.title;
         this.description = d.description;
         this.trackedId = d.trackedId;
         if (d.statusUpdatedOn) {
            this.statusUpdatedOn = new Date(d.statusUpdatedOn);
         }
         if (d.documents && Array.isArray(d.documents) && d.documents.length > 0) {
            this.documents = [];
            d.documents.forEach(a => {
               this.documents.push(new IssueDocument(a));
            });
         }
         if (d.currentStatus != null) {
            if (typeof d.currentStatus === 'string') {
               this.currentStatus = (<any>TrackingStatus)[d.currentStatus];
            } else {
               this.currentStatus = d.currentStatus;
            }
         }
      }
   }

   public override toString = (): string => {
      return this.title;
   }

}

export class IssueDocument {
   public id: string;
   public path: string;
   public uploadedOn: Date;

   constructor(d?: IssueDocument) {
      if (d != null) {
         this.id = d.id;
         this.path = d.path;
         if (d.uploadedOn != null) {
            this.uploadedOn = new Date(d.uploadedOn);
         }
      }
   }
}
