import { FormEditorOption } from './FormEditorOption';
export class MenuItems extends FormEditorOption {
  command: string;
  /**
   *
   */
  constructor(name, F?: FormEditorOption) {
    super(F);
    this.command = name;
  }
}
