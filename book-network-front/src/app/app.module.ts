import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClient, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { LoginComponent } from './components/login/login.component';
import {FormsModule} from "@angular/forms";
import { SignupComponent } from './components/signup/signup.component';
import { ActivateAccountComponent } from './components/activate-account/activate-account.component';
import { CodeInputModule } from "angular-code-input";

@NgModule({ declarations: [
        AppComponent,
        LoginComponent,
        SignupComponent,
        ActivateAccountComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        FormsModule,
        CodeInputModule], providers: [
        HttpClient,
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule { }
