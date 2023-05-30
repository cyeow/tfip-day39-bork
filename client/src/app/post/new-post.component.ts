import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PostRequest } from '../models';
import { ServerService } from '../server.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent {
  form!: FormGroup
  @ViewChild("imgFile")
  imgRef!: ElementRef

  fb = inject(FormBuilder)
  ss = inject(ServerService)
  router = inject(Router)

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm(): FormGroup {
    return this.fb.group({
      text: this.fb.control<string>('', [Validators.required]),
      img: this.fb.control<File | null>(null)
    });
  }

  submit(): void {
    const i: File = this.imgRef.nativeElement.files[0]
    // this.form.patchValue({img: i})
    const data: PostRequest = {
      ... this.form.value,
      img: i
    }

    this.ss.newPost(data)
      .then(result => {
        if (!!result) {
          let postId = result
          this.router.navigate(['/post', postId])
        } else {
          throw new Error('Error encountered shouting')
        }
      })
      .catch(err => alert(JSON.stringify(err)))

    
    // console.log(this.form.value);
  }
}
