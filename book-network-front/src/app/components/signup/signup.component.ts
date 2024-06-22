import { Component } from '@angular/core';
import {RegisterRequest} from "../../services/models/register-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  registerRequest: RegisterRequest = {
    email: '',
    password: '',
    firstName: '',
    lastName: ''
  };

  errorMessage: Array<String> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService) { }

  signup() {
    this.errorMessage = [];
    this.authService.register({
      body: this.registerRequest

    }).subscribe({
      next: () => {
        this.registerRequest = {
          email: '',
          password: '',
          firstName: '',
          lastName: ''
        };
        this.router.navigate(['activate-account']).then();
      },
      error: (error) => {
        this.errorMessage = error.error.validationErrors; // Intercept the error message from the backend
      }
    });
  }

  login() {
    this.router.navigate(['login']).then();
  }
}
