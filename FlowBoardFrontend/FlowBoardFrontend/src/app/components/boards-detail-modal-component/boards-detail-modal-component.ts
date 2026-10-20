import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BoardService } from '../../services/board-service';
import { BoardSummary } from '../../interfaces/board-summary';


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

  constructor(private boardService:BoardService){}
  
  boardDetailsForm = new FormGroup({
    title: new FormControl(this.title(), [Validators.required]),
    description: new FormControl(this.description(), [Validators.required])
  })
  
  sendBoardToBackend(){
      if(this.id() != null){
        this.boardService.updateBoard(this.boardDetailsForm.value.title, this.boardDetailsForm.value.description, this.id()).subscribe({
          next: (result: BoardSummary) => {this.dialogueRef.close(result)},
          error: () => this.showPopup()
        })
          }
      else{
        this.boardService.createNewBoard(this.boardDetailsForm.value.title, this.boardDetailsForm.value.description).subscribe({
          next: (result: BoardSummary) => {
            this.dialogueRef.close(result)},
          error: () => this.showPopup()
        })
      }
  }

  closeModal(){
      this.dialogueRef.close();
  }

  showPopup(){
    const message = 'Unknown error appeared!';
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }

}
