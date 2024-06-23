import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {AuthenticationService} from "../../services/services/authentication.service";
import {Router} from "@angular/router";
import {TokenService} from "../../services/token/token.service";
import {AuthenticationResponse} from "../../services/models/authentication-response";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessage: Array<String> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService

  ) { }

  login() {
    this.errorMessage = []; // Reset error messages when a user tries to log in again, after a previous failed attempt
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (response: AuthenticationResponse) => {

        this.tokenService.token = response.token as string; // Save the token in the local storage, cast it to string
        this.router.navigate(['books']).then();
      },
      error: (error) => {
        console.log(error);
        if (error.error.validationErrors) {
          this.errorMessage = error.error.validationErrors;
        } else {
          this.errorMessage.push(error.error.error);
        }
      }
    })

  }
  signUp() {
    this.router.navigate(['/register']).then(r => {});
  }
}
