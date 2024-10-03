import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './feature/main/main.component';
import {BookListComponent} from "./feature/book-list/book-list.component";
import {MyBooksComponent} from "./feature/my-books/my-books.component";
import {ManageBookComponent} from "./feature/manage-book/manage-book.component";
import {BorrowedBookListComponent} from "./feature/borrowed-book-list/borrowed-book-list.component";
import {ReturnedBooksComponent} from "./feature/returned-books/returned-books.component";
import {authGuard} from "../../services/guard/auth.guard";

const routes: Routes = [
  { path: '', title: 'Main-Page', component: MainComponent,
    children: [
      {
        path: '', title: 'Book-List' ,component: BookListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-books', title: 'My-Books', component: MyBooksComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-borrowed-books', title: 'My-Borrowed-Books', component: BorrowedBookListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-returned-books', title: 'My-Returned-Books', component: ReturnedBooksComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage', title: 'Manage-Book', component: ManageBookComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage/:bookId', title: 'Manage-Book-By-Id', component: ManageBookComponent,
        canActivate: [authGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
