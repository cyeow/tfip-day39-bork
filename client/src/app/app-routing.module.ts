import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewPostComponent } from './post/new-post.component';
import { PostComponent } from './post/post.component';
import { PostListComponent } from './post/post-list.component';

const routes: Routes = [
  {path: '', component: PostListComponent},
  {path: 'post', component: NewPostComponent},
  {path: 'post/:postId', component: PostComponent},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
