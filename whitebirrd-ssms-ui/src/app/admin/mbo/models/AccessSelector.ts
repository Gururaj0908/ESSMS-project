export class AccessSelector {
   id: number;
   userId: string;
   projectItemId: string;
   objectiveId: string;
   canView: boolean;
   canEdit: boolean;
   canApprove: boolean;

   constructor(d?: AccessSelector) {
      if (d) {
         this.id = d.id;
         this.userId = d.userId;
         this.projectItemId = d.projectItemId;
         this.canView = d.canView;
         this.canEdit = d.canEdit;
         this.canApprove = d.canApprove;
      }
   }
}
