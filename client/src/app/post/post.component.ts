import { Component, Input, OnChanges, OnDestroy, OnInit, inject } from '@angular/core';
import { ServerService } from '../server.service';
import { Observable, Subscription, firstValueFrom } from 'rxjs';
import { Post } from '../models';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit, OnChanges, OnDestroy {
  post$!: Observable<Post>
  postSub$!: Subscription

  @Input()
  postId!: string

  ss = inject(ServerService)
  activatedRoute = inject(ActivatedRoute)

  ngOnInit(): void {
    if (!this.postId) {
      this.postSub$ = this.activatedRoute.params.subscribe(p => this.postId = p['postId'])
    }
    this.post$ = this.ss.getPost(this.postId)
  }

  ngOnChanges(): void {
    // this.post$ = this.ss.getPost(this.postId)
  }

  ngOnDestroy(): void {
    if (!!this.postSub$) {
      this.postSub$.unsubscribe()
    }
  }

  upvote(): void {
    firstValueFrom(this.ss.upvote(this.postId))
      .then(
        _ => this.update()
      )
      .catch(
        err => console.error("Error upvoting.")
      )
  }

  downvote(): void {
    firstValueFrom(this.ss.downvote(this.postId))
      .then(
        _ => this.update()
      )
      .catch(
        err => console.error("Error downvoting.")
      )
  }

  update(): void {
    this.post$ = this.ss.getPost(this.postId)
  }
}
