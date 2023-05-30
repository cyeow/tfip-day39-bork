import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NewPostComponent } from './post/new-post.component';
import { PostComponent } from './post/post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ServerService } from './server.service';
import { PostListComponent } from './post/post-list.component';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    NewPostComponent,
    PostComponent,
    PostListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [ServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
