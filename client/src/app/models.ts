export interface PostRequest {
    text: string
    img: File
}

export interface Post {
    postId: string
    text: string
    imgUrl: string
    comments: Comment[]
    up: number
    down: number
    user: string
}

export interface Comment {
    commentId: string
    text: string
    user: string
}