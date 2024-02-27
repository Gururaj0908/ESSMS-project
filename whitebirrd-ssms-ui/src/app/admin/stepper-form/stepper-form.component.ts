import { Component, OnInit, Input } from '@angular/core';
import { FormList } from '../dynamic-form/FormList';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CoreFormOps } from '../mbo/form-area/core-form-ops';
import { RemoteService } from '../../services/remote.service';

@Component({
   selector: 'app-stepper-form',
   templateUrl: './stepper-form.component.html',
   styleUrls: ['./stepper-form.component.scss']
})
export class StepperFormComponent implements OnInit {

   @Input() Formlist: FormList[];

   public Form: StepperFormItem[] = [];

   constructor(private fb: FormBuilder, private remote: RemoteService) { }



   ngOnInit() {
      if (this.Formlist != null) {
         this.Formlist.forEach(fl => {
            this.Form.push(new StepperFormItem(this.fb, this.remote, null, fl));
         });
      }
   }

}

export class StepperFormItem {
   public Formlist: FormList;
   public Formgroup: FormGroup;
   constructor(fb: FormBuilder, remote: RemoteService, isNewForm: boolean, formlist: FormList) {
      const formInitPlayer = new CoreFormOps(fb, remote, isNewForm);
      // start generating form
      this.Formgroup = formInitPlayer.CreatFormGroup(formlist);
      this.Formlist = formlist;
   }
}
