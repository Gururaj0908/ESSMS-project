import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Constants } from '../../constants';
import { LanguageService } from '../../services/language.service';
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  theme = "my-theme";
  panelOpenState: boolean = false;
  panelOpenState1: boolean = false;
  colorConstant: any;
  userData: any;
  displayAdmin: boolean;
  signOut() {
    this.authService.signOut();
    this.router.navigate(['ecommerce/home']);
  }
  getUserData() {

    this.userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
  }
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
  constructor(public l: LanguageService, private authService: AuthService, private router: Router) {
    this.userData = JSON.parse(sessionStorage.getItem(Constants.LOGGED_USER));
  }

  ngOnInit() {
    this.getColorConstants();
    this.getUserData();
    if (this.userData.loginSuccess.userType === 'BACKOFFICE') {
      this.displayAdmin = true;
    }
  }

  getColorConstants() {
    this.colorConstant = {};
    this.colorConstant.header = {};
    this.colorConstant.header.background = "#7b1fa2";
    this.colorConstant.header.color = "#7b1fa2";
    this.colorConstant.Button = {
      'background-color': "#67148a",
      'color': '#fff',
      'border-bottom': '1px solid #fff',
      'margin-right': '10px'
    };

  }

}
