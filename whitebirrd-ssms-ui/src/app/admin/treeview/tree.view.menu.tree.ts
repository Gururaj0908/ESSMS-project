import { Tree } from "./tree";
import { BusinessObject } from "../model/business.object";
import { TreeViewItemImpl } from "../model/tree.view.item.impl";

export class TreeViewMenuTree extends Tree<TreeViewItemImpl> {
    constructor() {
        super();
    }
}