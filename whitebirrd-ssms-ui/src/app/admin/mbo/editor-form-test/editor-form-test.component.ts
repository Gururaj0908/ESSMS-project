import { Component, OnInit } from '@angular/core';
import { FormEditorModel } from '../../dynamic-form/FormEditor';
import { timer } from 'rxjs';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { FormList } from '../../dynamic-form/FormList';
import { FormEditorType } from '../../dynamic-form/FormEditorType';

@Component({
  selector: 'app-editor-form-test',
  templateUrl: './editor-form-test.component.html',
  styleUrls: ['./editor-form-test.component.scss']
})
export class EditorFormTestComponent implements OnInit {
  form: FormList;
  ngOnInit(): void {

  }
  /**
   *
   */
  constructor() {
    this.form = new FormList();
    this.form.forms = [];
    const price = new FormEditorModel();
    price.formEditorType = FormEditorType.PriceSelector;
    price.hint = 'This is hint';
    price.key = 'price';
    price.label = 'Choosen Price';
    price.placeHolder = 'PRICE';
    price.required = true;
    this.form.forms.push(price);
  }

}
