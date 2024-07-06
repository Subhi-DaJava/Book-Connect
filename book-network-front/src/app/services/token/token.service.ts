import { Injectable } from '@angular/core';

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

}
