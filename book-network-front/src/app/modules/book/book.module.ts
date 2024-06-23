import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './feature/main/main.component';
import { MenuComponent } from './components/menu/menu.component';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    BookRoutingModule,
    MainComponent,
    MenuComponent
  ]
})
export class BookModule { }
