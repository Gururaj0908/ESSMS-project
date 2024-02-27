import { Component, OnInit } from '@angular/core';
import { LanguageService } from '../../../services/language.service';

@Component({
  selector: 'app-theme-settings',
  templateUrl: './theme-settings.component.html',
  styleUrls: ['./theme-settings.component.scss']
})
export class ThemeSettingsComponent implements OnInit {
  panelOpenState = false;
  panelOpenState1 = true;
  favoriteSeason: string;
  favoriteSeason1: string;
  favoriteSeason2: string;
  seasons: string[] = ['Style 1', 'Style 2'];
  navLinks = [
    {
      'label': this.l.GetString('Home'),
      'path': '/ecommerce/home'
    },
    {
      'label': this.l.GetString('RepairStatus'),
      'path': '/ecommerce/product-category'
    },
    {
      'label': this.l.GetString('BookRepair'),
      'path': '/ecommerce/book-product-repair'
    },
    {
      'label': this.l.GetString('FakeProduct'),
      'path': '/ecommerce/fake-product-identification'
    },

  ];
  constructor(public l: LanguageService,) { }

  ngOnInit() {
    this.createTheme();
  }


  createTheme(){
    const style = document.createElement('style');
style.type = 'text/css';
style.innerHTML = '.cssClass { color: #F00; }';
// document.getElementsByTagName('head')[0].appendChild(style);

// document.getElementById('someElementId').className = 'cssClass';
  }
}
