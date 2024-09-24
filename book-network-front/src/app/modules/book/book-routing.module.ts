import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './feature/main/main.component';
import {BookListComponent} from "./feature/book-list/book-list.component";
import {MyBooksComponent} from "./feature/my-books/my-books.component";

const routes: Routes = [
  { path: '', title: 'Main-Page', component: MainComponent,
    children: [
      {
        path: '', title: 'Book-List' ,component: BookListComponent
      },
      {
        path: 'my-books', title: 'My-Books', component: MyBooksComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
