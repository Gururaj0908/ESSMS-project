//#region Helper entities
export interface Itreeable {
   parent: Itreeable;
   children: Itreeable[];
   id: any;
   selected: boolean;
   selectable: boolean;
}

