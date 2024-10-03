import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  // setter method
  set token(token: string) {
    sessionStorage.setItem('token', token);
  }

  // getter method
  get token() {
    return sessionStorage.getItem('token') as string;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    // decode the token and check the expiration date, install auth0/angular-jwt
    const jwtHelper: JwtHelperService = new JwtHelperService();
    // check if the token is expired
    const isTokenExpired = jwtHelper.isTokenExpired(token);

    if(isTokenExpired) {
      sessionStorage.removeItem('token');
      return false;
    }
    return true;
  }
}
