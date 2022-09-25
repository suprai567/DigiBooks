export enum BookCategory {
    ADVENTURE = 'ADVENTURE',
    ACTION = 'ACTION',
    THRILLER = 'THRILLER',
    FICTION = 'FICTION',
    COMEDY = 'COMEDY',
    ROMANTIC = 'ROMANTIC'
}

export default class Book{
    constructor(public logo: String, public title: String, public category: BookCategory, 
        public price: number, public authorUserName: String, public authorName: String, 
        public publisher: String, public publishedDate: Date, public content: String, 
        public active: Boolean){ }
}