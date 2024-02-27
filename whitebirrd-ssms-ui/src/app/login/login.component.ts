import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RemoteService } from '../services/remote.service';
import { AuthService } from '../services/auth.service';
import { MatSnackBar} from '@angular/material/snack-bar';
import { tap } from 'rxjs/operators';
import { LoginTimerService } from '../services/login-timer.service';
import { Constants } from '../constants';
import { LanguageService } from '../services/language.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  title = 'eSSMS';
  model: any = {};
  loading = false;
  returnUrl: string;
  fullImagePath: string;
  openRegister = false;
  onAdd = new EventEmitter();
  colorConstant: any;
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
  theme = 'my-theme';
  LoginForm = new FormGroup({
    UserName: new FormControl('', [Validators.required]),
    Password: new FormControl('', [Validators.required]),
  });

  RegisterForm = new FormGroup({
    Name: new FormControl('', [Validators.required]),
    EmailId: new FormControl('', [Validators.required]),
    MobileNo: new FormControl('', [Validators.required]),
    UserName: new FormControl('', [Validators.required]),
    Password: new FormControl('', [Validators.required]),
    ConfirmPassword: new FormControl('', [Validators.required]),
  });
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private refresh: LoginTimerService,
    public httpClient: HttpClient,
    private remoteService: RemoteService,
    private authService: AuthService,
    private snackbar: MatSnackBar,
    public l: LanguageService
    // private dialogRef: MatDialogRef<LoginComponent>, @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.fullImagePath = 'assets/image/essms.png';
  }

  ngOnInit() {
    // reset login status
    this.authService.signOut();
    this.getColorConstants();
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  tabSwitch() {
    this.openRegister = !this.openRegister;
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
  login() {
    this.loading = true;
    this.remoteService.post('/essms-auth/authentication/login',
      { username: this.model.username, password: this.model.password }).subscribe(
        data => {
          console.log('Login Response Data ::::::: ' + data);
          sessionStorage.setItem(Constants.LOGGED_USER, JSON.stringify(data));
          if (!this.refresh.Running) {
            this.refresh.StartTimer();
          }

          // this.router.navigate([this.returnUrl]);
          if (data.loginSuccess.access_token) {
            //this.dialogRef.close();

            if (data.loginSuccess.userType === 'CUSTOMER') {
              this.onAdd.emit();
              this.router.navigate(['ecommerce/home']);
            } else {
              this.router.navigate(['admin/dashboard']);
            }
            this.loading = false;
          } else {
            console.error(data.error);
          }

        },
        error => {
          console.error(error);
          console.log(JSON.stringify(error));
          this.snackbar.open('Login Failed, Reason : ' + error.error.loginError.error_description, '', {
            duration: 2000
          });
          this.loading = false;
        });
  }

  passwordReveal(bool, password) {
    password.type = bool ? 'text' : 'password';
  }


  register() {
    this.loading = true;
    this.remoteService.post('/essms-auth/user/signup', this.model)
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {
          console.error(error);
          this.loading = false;
        });
  }



  nameError() {
    return this.RegisterForm.hasError('required', ['Name']) ? 'Name is Required' : '';
  }

  emailError() {
    return this.RegisterForm.hasError('required', ['EmailId']) ? 'Email Id is Required' : '';
  }

  mobileNoError() {
    return this.RegisterForm.hasError('required', ['MobileNo']) ? 'Mobile No is Required' : '';
  }

  usernameError() {
    return this.RegisterForm.hasError('required', ['UserName']) ? 'User Name is Required' : '';
  }

  passwordError() {
    return this.RegisterForm.hasError('required', ['Password']) ? 'Password is Required' : '';
  }

  confirmPasswordError() {
    return this.RegisterForm.hasError('required', ['ConfirmPassword']) ? 'Confirm Password is Required' : '';
  }
  post(url: string, data: any) {
    console.log('Posting a data', data);
    return this.httpClient.post<any>('http://essms.horolab.in/' + url, data).pipe(
      tap( // Log the result or error
        returnData => console.log('Received data', returnData),
        error => console.error(error)
      )
    );
  }




}
