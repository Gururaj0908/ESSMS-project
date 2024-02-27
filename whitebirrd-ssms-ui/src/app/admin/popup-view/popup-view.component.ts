import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GlobalService } from '../../services/global.service';
import { RemoteService } from '../../services/remote.service';
import { FormList } from '../dynamic-form/FormList';
import { ContentType } from './ContentType';
import { DetailModel } from './DetailModel';
import { PopupDataArgs } from './PopupDataArgs';
import { PopupType } from './PopupType';
import { RouteType } from '../model/RouteType';


@Component({
    selector: 'app-popup-view',
    templateUrl: './popup-view.component.html',
    styleUrls: ['./popup-view.component.scss']
})
export class PopupViewComponent implements OnInit {

    public Form: FormList;
    public Loading: boolean;
    public popupType = PopupType;
    public contentType = ContentType;
    public DisplayList = false;

    @Output() FormExecuted = new EventEmitter<any>();

    constructor(
        public dialogRef: MatDialogRef<PopupViewComponent>,
        @Inject(MAT_DIALOG_DATA) public data: PopupDataArgs,
        private remote: RemoteService, public gst: GlobalService) {
        if (data.Url && data.Type === PopupType.Remote) {
            this.Loading = true;
            remote.get(data.Url).subscribe(
                response => {
                    if (Array.isArray(response)) {
                        if (data.Data == null) {
                            data.Data = [];
                        }
                        response.forEach(r => {
                            const d = new DetailModel(r);
                            data.Data.push(d);
                        });
                    } else if (data.route != null && data.route === RouteType.List) {
                        this.DisplayList = true;
                    } else {
                        this.Form = response;
                    }
                    this.Loading = false;
                },
                error => {
                    remote.logInformationToConsole(true, true, 'Popup data GET-API error', error);
                    this.Loading = false;
                }
            );
        }
        this.Form = data.Form;
    }

    ngOnInit() {
    }

    protected HideForm() {
        this.dialogRef.close();
    }

    protected SubmitForm(Formgroup: FormGroup) {
        const d = Formgroup.getRawValue();
        this.data.callBack(d, this.data.cb_parm, Formgroup, this.dialogRef);
    }



}
