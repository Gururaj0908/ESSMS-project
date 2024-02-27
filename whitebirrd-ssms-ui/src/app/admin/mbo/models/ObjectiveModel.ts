import { ObjectiveStatus } from './ObjectiveStatus';
import { ObjectivePriority } from './ObjectivePriority';
import { BaseModel } from './BaseModel';

export class ObjectiveModel extends BaseModel {

   title: string;
   rank: number;
   priority: ObjectivePriority;
   assignedTo: string;
   startDate: Date;
   minutes: number;
   dueDate: Date;
   completeOn: Date;
   status: ObjectiveStatus;
   description: string;
   remarks: string;
   dependsOnId: string;
   extensionProcessId: string;
   projectItemId: string;

   constructor(d?: ObjectiveModel) {
      super(d);
      if (d != null) {
         this.title = d.title;
         this.rank = d.rank;
         if (d.priority != null) {
            if (typeof d.priority === 'string') {
               this.priority = (<any>ObjectivePriority)[d.priority];
            } else {
               this.priority = d.priority;
            }
         }
         this.assignedTo = d.assignedTo;
         if (d.startDate != null) {
            this.startDate = new Date(d.startDate);
         }
         this.minutes = d.minutes;
         if (d.dueDate) {
            this.dueDate = new Date(d.dueDate);
         }
         if (d.completeOn) {
            this.completeOn = new Date(d.completeOn);
         }
         if (d.status != null) {
            if (typeof d.status === 'string') {
               this.status = (<any>ObjectiveStatus)[d.status];
            } else {
               this.status = d.status;
            }
         }
         this.description = d.description;
         this.remarks = d.remarks;
         this.projectItemId = d.projectItemId;
         this.dependsOnId = d.dependsOnId;
         this.extensionProcessId = d.extensionProcessId;
      }
   }

   public override toString = (): string => {
      return this.title;
   }
}

