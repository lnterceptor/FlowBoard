import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BoardService } from '../../services/board-service';
import { BoardSummary } from '../../interfaces/board-summary';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-boards-detail-modal-component',
  imports: [MatDialogModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  templateUrl: './boards-detail-modal-component.html',
  styleUrl: './boards-detail-modal-component.scss',
})
export class BoardsDetailModalComponent {
  data = inject(MAT_DIALOG_DATA);
  title = signal(this.data.title);
  description = signal(this.data.description);
  id = signal(this.data.id);
  private dialogueRef = inject(MatDialogRef);

  constructor(private boardService:BoardService, public snackBar: MatSnackBar){}
  
  boardDetailsForm = new FormGroup({
    title: new FormControl(this.title(), [Validators.required]),
    description: new FormControl(this.description(), [Validators.required])
  })
  
  sendBoardToBackend(){
    if(this.boardDetailsForm.value.title.length < 3){
      this.showPopup("Title must be at least 3 characters long")
      return
    }
    else if(this.boardDetailsForm.value.title.length > 50){
      this.showPopup("Title must be shorter than 50 characters")
      return
    }

      if(this.id() != null){
        this.boardService.updateBoard(this.boardDetailsForm.value.title, this.boardDetailsForm.value.description, this.id()).subscribe({
          next: (result: BoardSummary) => {this.dialogueRef.close(result)},
          error: (error: HttpErrorResponse) => this.showPopup(error.error.message)
        })
          }
      else{
        this.boardService.createNewBoard(this.boardDetailsForm.value.title, this.boardDetailsForm.value.description).subscribe({
          next: (result: BoardSummary) => {
            this.dialogueRef.close(result)},
          error: (error: HttpErrorResponse) => this.showPopup(error.error.message)
        })
      }
  }

  closeModal(){
      this.dialogueRef.close();
  }

  showPopup(message: string){    
    this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
  }

}
