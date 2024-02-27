import { TreeItem } from '@yberion/ngx-treeview';

export class TreeViewItemImpl implements TreeItem {

  text: string;
  value: any;
  disabled?: boolean;
  checked?: boolean;
  collapsed?: boolean;
  children?: TreeViewItemImpl[];
}
