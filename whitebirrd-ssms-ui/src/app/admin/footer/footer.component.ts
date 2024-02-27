import { Component } from '@angular/core';
import { GlobalService } from '../../services/global.service';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {
  public Year: number;
  public Company: string;
  constructor(public gs: GlobalService, public l: LanguageService) {
    this.Year = new Date().getFullYear();
    this.Company = gs.TENANT_ID === 'bms' ? 'BMS' : l.GetString('AppName');
  }
}
