import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RemoteService } from '../services/remote.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
    selector: 'app-register',
    templateUrl: 'register.component.html',
    styleUrls: ['./../login/login.component.scss']
})

export class RegisterComponent {
    model: any = {};
    loading = false;
    fullImagePath: string;
    title = 'eSSMS';
    constructor(
        private router: Router,
        private remoteService: RemoteService) {
        this.fullImagePath = 'assets/image/essms.png';
    }
    RegisterForm = new FormGroup({
        Name: new FormControl('', [Validators.required]),
        EmailId: new FormControl('', [Validators.required]),
        MobileNo: new FormControl('', [Validators.required]),
        UserName: new FormControl('', [Validators.required]),
        Password: new FormControl('', [Validators.required]),
        ConfirmPassword: new FormControl('', [Validators.required]),
    });
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

}
