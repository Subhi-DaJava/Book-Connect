import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './feature/main/main.component';
import {BookListComponent} from "./feature/book-list/book-list.component";
import {MyBooksComponent} from "./feature/my-books/my-books.component";
import {ManageBookComponent} from "./feature/manage-book/manage-book.component";
import {BorrowedBookListComponent} from "./feature/borrowed-book-list/borrowed-book-list.component";

const routes: Routes = [
  { path: '', title: 'Main-Page', component: MainComponent,
    children: [
      {
        path: '', title: 'Book-List' ,component: BookListComponent
      },
      {
        path: 'my-books', title: 'My-Books', component: MyBooksComponent
      },{
        path: 'my-borrowed-books', title: 'My-Borrowed-Books', component: BorrowedBookListComponent
      },
      {
        path: 'manage', title: 'Manage-Book', component: ManageBookComponent
      },
      {
        path: 'manage/:bookId', title: 'Manage-Book-By-Id', component: ManageBookComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
