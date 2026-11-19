import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardService } from '../../services/card-service';
import { MatDatepickerModule} from '@angular/material/datepicker'
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import {MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { provideNativeDateAdapter  } from '@angular/material/core';
import { CardData } from '../../interfaces/card-data';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { LabelData } from '../../interfaces/label-data';
import {MatCheckboxModule } from '@angular/material/checkbox';
import { CommentData } from '../../interfaces/comment-data';
import { AuthService } from '../../services/auth-service';
import { User } from '../../interfaces/user';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WebSocketService } from '../../services/web-socket-service';
import { AddComment, BoardEvent, DeleteComment, UpdateComment } from '../../interfaces/board-event';

@Component({
  selector: 'app-card-detail-modal-component',
  imports: [MatCheckboxModule, CommonModule, FormsModule, MatChipsModule, MatIconModule, MatNativeDateModule, MatDialogModule, MatDatepickerModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  providers: [provideNativeDateAdapter()],
  templateUrl: './card-detail-modal-component.html',
  styleUrl: './card-detail-modal-component.scss',
})
export class CardDetailModalComponent {
  private cardService = inject(CardService);
  data = inject(MAT_DIALOG_DATA);
  readonly id = signal(this.data.id);
  description = signal(this.data.description);
  title = signal(this.data.title);
  dueDate = signal(this.data.dueDate);
  columnId = signal(this.data.columnId);
  private dialogueRef = inject(MatDialogRef);
  assignees = signal(this.data.assignees);
  collaborators = signal(this.data.collaborators);
  avaibleLabels = signal<LabelData[]>(this.data.avaibleLabels);
  assignedLabels = signal<LabelData[]>(this.data.assignedLabels);
  done = signal<boolean>(this.data.done);
  comments = signal<CommentData[]>([]);
  editingCommentId: number | null = null;
  user : User | null = null;
  boardId = signal(this.data.boardId);
  
  authService = inject(AuthService);
  confirmDelete = false;

  constructor(private http:HttpClient, public snackBar: MatSnackBar, private webSocketService: WebSocketService){
    this.authService.currentUser.subscribe(
      user => this.user = user
    )
  }


  assignedLabelNames(){
    return this.assignedLabels().map(label => label.name)
  }
  ngOnInit(){
    if(this.id()){
      this.getComments();
    }
    
    this.webSocketService.watchCard(this.boardId(), this.id()).subscribe(
      message => {
        const event = JSON.parse(message.body);
        
        this.handleCommentEvent(event);
      }
    )
  }

  handleCommentEvent(event: BoardEvent){
    
    
      switch(event.type){
        case 'COMMENT_ADDED':
          this.handleCommentAdded(event);
          break;
        case 'COMMENT_DELETED':
          this.handleCommentDeleted(event);
          break;
        case 'COMMENT_UPDATED':
          this.handleCommentUpdate(event);
          break;
      }
  }

  handleCommentAdded(event: AddComment){
    if(this.comments().find(comment => comment.commentId === event.commentId)){
      console.log("Comment added previously");
      
    }
    else{
      let comment : CommentData = {} as CommentData;
      comment.commentId = event.commentId;
      comment.content = event.content;
      comment.userId = event.userId;
      comment.username = event.username;
      this.comments.update(comments => [...comments, comment]);
   }
  }

  handleCommentDeleted(event: DeleteComment){
    this.comments.update(comments => comments.filter(comment => comment.commentId !== event.commentId))
  }
  handleCommentUpdate(event: UpdateComment){
    const comment = this.comments().find(comment => comment.commentId === event.commentId);
    comment!.content = event.content;
  }

  getComments(){
      this.cardService.getComments(this.id()).subscribe({
        next:(result: CommentData[]) => {
          this.comments.set(result);
        }
      })
  }

  addComment(comment: string){
    this.cardService.postComment(this.id(), this.user?.userId!, comment).subscribe({
      next:(result: CommentData) => {
        if(this.comments().find(comment => comment.commentId === result.commentId)){
          console.log("Comment added previously using sockets"); 
        }
        else{
          this.comments.update(comments => [...comments, result])
        }
      }
    })
    
  }

  startEditingComment(commentId: number, userId: number) {
    if(this.user?.userId === userId){
      this.editingCommentId = commentId;
    }
  }

updateComment(commentId: number, newContent: string) {
  this.cardService.updateComment(this.id(), this.user?.userId!, newContent, commentId).subscribe({
        next:(result: CommentData) => {this.comments.update(comments => comments.map(comment => comment.commentId !== result.commentId ? comment: result))},
        error:(error: HttpErrorResponse) => {this.showPopup(error.error.message)}
      })
  this.editingCommentId = null;
}


  deleteComment(commentId: number){
    this.cardService.deleteComment(this.id(), commentId, this.user?.userId!).subscribe({
      next:() => { this.comments.update(comments => comments.filter(comment => comment.commentId != commentId)) },
      error:(error: HttpErrorResponse) => {this.showPopup(error.error.message)}
    });
  }

  cardDetailsForm = new FormGroup({
    title: new FormControl(this.title(), [Validators.required]),
    dueDate: new FormControl(this.dueDate(), [Validators.required]),
    description: new FormControl(this.description()),  
    isDone: new FormControl(this.done() ?? false)
  })

  sendCardToBackend(){
    if(this.id() != null){
      this.updateCard();
    }
    else{
      this.addNewCard();
    }
  }


  addNewCard(){
    if(this.cardDetailsForm.value.title == null){
      this.showPopup("Card title cannot be left empty")
      return;
    }

    else if(this.cardDetailsForm.value.dueDate == null){
      this.showPopup("Due date cannot be left empty")
      return;
    }
    const newCard = this.cardService.addNewCard(this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees(), this.assignedLabels(),  this.cardDetailsForm.value.isDone!).subscribe({
      next: (result: CardData) => {
        this.dialogueRef.close(result)},
        error: (error: HttpErrorResponse) => {this.showPopup(error.error.message)}
    })
  }

  updateCard(){
    if(this.cardDetailsForm.value.title == null){
      this.showPopup("Card title cannot be left empty")
      return;
    }

    else if(this.cardDetailsForm.value.dueDate == null){
      this.showPopup("Due date cannot be left empty")
      return;
    }

    const updatedCard = this.cardService.updateCard(this.id(), this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees(), this.assignedLabels(), this.cardDetailsForm.value.isDone!).subscribe({
      next: (result: CardData) => {this.dialogueRef.close(result)},
      error: (error: HttpErrorResponse) => {this.showPopup(error.error.message)}
    })
  }

  showPopup(message: string){    
    this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
  }

  closeModal(){
    this.dialogueRef.close();
  }

  deleteCard(){
    this.cardService.deleteCard(this.id()).subscribe({
      next: () => {
        this.confirmDelete = false;
        this.dialogueRef.close(this.id())
      },
      error: (error: HttpErrorResponse) => {this.showPopup(error.error.message)}
    })
  }

  compareLabels(a: LabelData, b: LabelData): boolean{
   if(a && b && a.name === b.name){
      return true;
   }
   return false;
  }
}
