import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';
import {inject} from "@angular/core";
import {TokenService} from "../token/token.service";

export const jwtInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = inject(TokenService).token;
  console.log(authToken);

  if (authToken) {
    const authReq = req.clone({
      headers: new HttpHeaders({
        'Authorization': `Bearer ${authToken}`
      })
    });
    return next(authReq);
  }
  return next(req);
};
