import { BaseModel } from './BaseModel';

export class RequirementModel extends BaseModel {


   public projectItemId: string;
   public title: string;
   public description: string;
   public attachments: RequirementDocument[];
   public trackedId: string;
   public statusUpdatedOn: Date;
   public currentStatus: TrackingStatus;

   constructor(d?: RequirementModel) {
      super(d);
      if (d != null) {
         this.projectItemId = d.projectItemId;
         this.title = d.title;
         this.description = d.description;
         this.trackedId = d.trackedId;
         if (d.statusUpdatedOn) {
            this.statusUpdatedOn = new Date(d.statusUpdatedOn);
         }
         if (d.attachments && Array.isArray(d.attachments) && d.attachments.length > 0) {
            this.attachments = [];
            d.attachments.forEach(a => {
               this.attachments.push(new RequirementDocument(a));
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

export class RequirementDocument {
   public id: string;
   public path: string;
   public uploadedOn: Date;

   constructor(d?: RequirementDocument) {
      if (d != null) {
         this.id = d.id;
         this.path = d.path;
         if (d.uploadedOn != null) {
            this.uploadedOn = new Date(d.uploadedOn);
         }
      }
   }
}

export enum TrackingStatus {
   Raised = 0,
   Analysis = 1,
   Assigned = 2,
   Accepted = 3,
   Denied = 4,
   InDev = 5,
   Testing = 6,
   Released = 7,
   Closed = 8
}
