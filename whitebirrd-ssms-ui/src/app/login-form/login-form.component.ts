import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RemoteService } from '../services/remote.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Constants } from '../constants';


@Component({
    selector: 'app-login-form',
    templateUrl: './login-form.component.html',
    styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

    title = 'BMS';
    // model: any = {};
    loading = false;
    returnUrl: string;
    fullImagePath: string;
    LoginForm = new FormGroup({
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
    });


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private remoteService: RemoteService,
        private snackbar: MatSnackBar, ) {
    }

    ngOnInit() {
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || 'admin/dashboard';
    }

    login() {
        this.loading = true;
        this.remoteService.post('/essms-auth/authentication/login', this.LoginForm.getRawValue()).subscribe(
            data => {
                console.log(data);
                sessionStorage.setItem(Constants.LOGGED_USER, JSON.stringify(data));
                this.loading = false;
                if (data.loginSuccess.access_token) {
                    this.router.navigate([this.returnUrl]);
                } else {
                    this.snackbar.open('Login Failed', '', {
                        duration: 5000
                    });
                }

            },
            error => {
                console.log(JSON.stringify(error));
                this.snackbar.open('Login Failed, Reason : ' + error.error.loginError.error_description, '', {
                    duration: 5000
                });
                this.loading = false;
            });
    }

    passwordReveal(bool, password) {
        password.type = bool ? 'text' : 'password';
    }

    usernameError() {
        return this.LoginForm.hasError('required', ['username']) ? 'User Name is Required' : '';
    }

    passwordError() {
        return this.LoginForm.hasError('required', ['password']) ? 'Password is Required' : '';
    }
}
