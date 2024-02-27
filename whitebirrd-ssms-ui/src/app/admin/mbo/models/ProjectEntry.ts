import { AdditionalProperty } from './AdditionalProperty';
import { BaseModel } from './BaseModel';
import { ObjectiveModel } from './ObjectiveModel';
import { ProjectStage } from '../enum/ProjectStage';
import { AccessSelector } from './AccessSelector';
import { RequirementModel } from './RequirementModel';
import { IssuesModel } from './IssueModel';

export class ProjectEntry extends BaseModel {

   title: string;
   description: string;
   startDate: Date;
   completedOn: Date;
   expectedCompletionDate: Date;
   stage: ProjectStage;
   currentStatus: Array<number>;
   parentId: string;
   moreProperties: AdditionalProperty[];
   children: ProjectEntry[];
   objectives: ObjectiveModel[];
   accessControl: AccessSelector[];
   parentEntry: ProjectEntry;
   requirements: RequirementModel[];
   issues: IssuesModel[];

   constructor(d?: ProjectEntry) {
      super(d);
      if (d) {
         this.title = d.title;
         if (d.accessControl != null) {
            this.accessControl = [];
            d.accessControl.forEach(a => {
               this.accessControl.push(new AccessSelector(a));
            });
         }
         this.description = d.description;
         if (d.startDate != null) {
            this.startDate = new Date(d.startDate);
         }
         if (d.completedOn != null) {
            this.completedOn = new Date(d.completedOn);
         }
         if (d.stage != null) {
            if (typeof d.stage === 'string') {
               this.stage = (<any>ProjectStage)[d.stage];
            } else {
               this.stage = d.stage;
            }
         }
         this.parentId = d.parentId;
         if (d.moreProperties) {
            this.moreProperties = [];
            d.moreProperties.forEach(mp => {
               this.moreProperties.push(new AdditionalProperty(mp));
            });
         }
         if (d.children != null) {
            this.children = [];
            d.children.forEach(c => {
               this.children.push(new ProjectEntry(c));
            });
         }
         if (d.objectives != null) {
            this.objectives = [];
            d.objectives.forEach(obj => {
               this.objectives.push(new ObjectiveModel(obj));
            });
         }
         if (d.expectedCompletionDate != null) {
            this.expectedCompletionDate = new Date(d.expectedCompletionDate);
         }
         this.currentStatus = d.currentStatus;
         if (d.requirements && Array.isArray(d.requirements) && d.requirements.length > 0) {
            this.requirements = [];
            d.requirements.forEach(r => {
               this.requirements.push(new RequirementModel(r));
            });
         }
         if (d.issues && Array.isArray(d.issues) && d.issues.length > 0) {
            this.issues = [];
            d.issues.forEach(i => {
               this.issues.push(new IssuesModel(i));
            });
         }
      }
   }
   public override toString = (): string => {
      return this.title;
   }

}
