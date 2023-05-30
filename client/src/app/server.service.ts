import { HttpClient, HttpParams } from "@angular/common/http";
import { inject } from "@angular/core";
import { Post, PostRequest } from "./models";
import { Observable, firstValueFrom } from "rxjs";

export class ServerService {

    endpointPost: string = '/api/post'
    endpointPosts: string = '/api/posts'

    http = inject(HttpClient)
    // returns postId
    newPost(p: PostRequest): Promise<string | void> {
        const formData = new FormData()
        formData.set('text', p.text)
        formData.set('img', p.img)
        return firstValueFrom(this.http.post<{ id: string }>(this.endpointPost, formData))
            .then(p => p.id)
            .catch(err => {
                console.error(err)
            })
    }

    getPost(postId: string): Observable<Post> {
        const params = new HttpParams()
            .set("postId", postId)

        return this.http.get<Post>(this.endpointPost, { params })
    }

    getPostIds(pageNum: number, limit: number) {
        const params = new HttpParams()
            .set("limit", limit)
            .set("pageNum", pageNum)

        return this.http.get<string[]>(this.endpointPosts, { params })
    }

    upvote(postId: string): Observable<any> {
        return this.http.put<any>(this.endpointPost + '/' + postId + '/up', {})
    }

    downvote(postId: string): Observable<any> {
        return this.http.put<any>(this.endpointPost + '/' + postId + '/down', {})
    }
}