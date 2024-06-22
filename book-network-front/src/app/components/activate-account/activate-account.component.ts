import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {

  errorMessage: string = '';
  message: string = '';
  isValid: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) { }

  onCompleted(token: string) {
    this.confirmNewAccount(token);
  }

  navigateToLogin() {
    this.router.navigate(['login']).then();
  }

  private confirmNewAccount(token: string) {
    // param is token and value is token, so we can use token only
    this.authService.activateAccount({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account is now activated. You can now sign in!';
        this.submitted = true;
        this.isValid = true;
      },
      error: () => {
        this.errorMessage = 'Invalid token or Token is expired. Please try again.';
        this.submitted = true;
        this.isValid = false;
      }
    });
  }
}
