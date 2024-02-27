import { Component, Input, Output, EventEmitter, ViewChild, OnInit, AfterViewInit, AfterContentInit } from '@angular/core';
import { FormList } from '../dynamic-form/FormList';
import { TreeViewComponent } from '../mbo/tree-view/tree-view.component';
import { TreeViewActionEventArgs } from '../mbo/tree-view/TreeViewActionEventArgs';
import { Itreeable } from '../mbo/tree-view/Itreeable';
import { FormGroup } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import * as cloneDeep from 'lodash/cloneDeep';

@Component({
   selector: 'app-tree-area',
   templateUrl: './tree-area.component.html',
   styleUrls: ['./tree-area.component.scss']
})
export class TreeAreaComponent implements AfterContentInit {

   //#region Data Bindable properties

   private _Data: any;
   @Input()
   public get Data(): any {
      return this._Data;
   }
   public set Data(v: any) {
      this._Data = v;
      this._filteredData = v;
   }

   private _Form: FormList;
   @Input()
   public get Form(): FormList {
      return this._Form;
   }
   public set Form(v: FormList) {
      this._Form = v;
      if (v && this.TitleKey) {
         this._filterHint = `Filter will be done in ${v.forms.find(t => t.key === this.TitleKey).label}`;
      }
   }

   @Input() TitleKey: string;
   @Input() SubtitleKey: string;
   @Input() TooltipKey: string;
   @Input() ParentIdKey: string;

   @Input() ShowChildCount: boolean;
   @Input() CreateNodeAllowed: boolean;
   @Input() UpdateNodeAllowed: boolean;
   @Input() DeleteNodeAllowed: boolean;
   @Input() Disabled: boolean;

   @Input() SelectionAllowed: boolean;

   @Output() DeleteNode = new EventEmitter<TreeViewActionEventArgs>();
   @Output() UpdateNode = new EventEmitter<TreeViewActionEventArgs>();
   @Output() InsertNode = new EventEmitter<TreeViewActionEventArgs>();

   @ViewChild('treeview', {static: false}) Treeview: TreeViewComponent;
   //#endregion

   //#region Variables
   _filteredData: any;
   SelectedData = [];
   _filterHint: string;
   ExpandAll = true;

   public _connectedChildren = false;

   //#endregion

   //#region event handlers
   ngAfterContentInit(): void {
      this._connectedChildren = true;
   }
   public Delete(event) {
      this.DeleteNode.emit(event);
   }

   public Insert(event) {
      this.InsertNode.emit(event);
   }


   public get SelectedIds(): Itreeable[] {
      if (this.Treeview != null) {
         return this.Treeview.SelectedIds;
      } else {
         return null;
      }
   }


   public InsertRoot(event: FormGroup) {
      this.InsertNode.emit(
         new TreeViewActionEventArgs(this.Treeview, event.getRawValue())
      );
   }

   public Update(event) {
      this.UpdateNode.emit(event);
   }

   public Checked(event: MatSlideToggleChange) {
      this._connectedChildren = event.checked;
   }

   public ExpansionChange(event: MatSlideToggleChange) {
      this.Treeview._internalOpen = event.checked;
   }
   //#endregion

   public filterData(inp) {
      this.Treeview._internalOpen = true;
      const filterText = inp.currentTarget.value;
      this._filteredData = this.GetFilteredTree(cloneDeep(this.Data), filterText);
   }

   private GetFilteredTree(t: Itreeable, f: string): Itreeable {
      let tree: Itreeable;
      if (!this.doesThisNodeMatch(t, f)) {
         return null;
      }
      if (t[this.TitleKey].toLowerCase().indexOf(f.toLowerCase()) >= 0) {
         return t;
      }
      tree = t;
      const children: Itreeable[] = [];
      for (let i = 0; i < t.children.length; i++) {
         const tt = t.children[i];
         if (this.GetFilteredTree(tt, f) != null) {
            children.push(tt);
         }
      }
      tree.children = children;
      return tree;
   }

   private doesThisNodeMatch(n: Itreeable, t: string) {
      if (n[this.TitleKey].toLowerCase().indexOf(t.toLowerCase()) >= 0) {
         return true;
      } else {
         if (n.children == null || n.children.length === 0) {
            return false;
         } else {
            let result = false;
            for (let i = 0; i < n.children.length; i++) {
               const element = n.children[i];
               result = result || this.doesThisNodeMatch(element, t);
            }
            return result;
         }
      }

   }
}
