import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {SignupComponent} from "./components/signup/signup.component";
import {ActivateAccountComponent} from "./components/activate-account/activate-account.component";

const routes: Routes = [
  { "path": "login", "component": LoginComponent },
  { "path": "register", "component": SignupComponent },
  { "path": "activate-account", "component": ActivateAccountComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
