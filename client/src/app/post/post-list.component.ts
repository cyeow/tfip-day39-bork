import { Component, inject } from '@angular/core';
import { ServerService } from '../server.service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent {
  pageNum: number = 1
  limit: number = 5

  postIDs$!: Observable<string[]>
  
  ss = inject(ServerService)

  ngOnInit(): void {
    this.postIDs$ = this.ss.getPostIds(this.pageNum, this.limit);
  }
}
