import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ColumnService } from '../../services/column-service';
import { ColumnData } from '../../interfaces/column-data';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-column-detail-modal-component',
  imports: [MatDialogModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  templateUrl: './column-detail-modal-component.html',
  styleUrl: './column-detail-modal-component.scss',
})
export class ColumnDetailModalComponent {
  data = inject(MAT_DIALOG_DATA);
  title = signal(this.data.title);
  id = signal(this.data.id);
  boardId = signal(this.data.boardId);
  private dialogueRef = inject(MatDialogRef);
  confirmDelete = false;
  
  constructor(private columnService:ColumnService, public snackBar: MatSnackBar){}
  columnDetailsForm = new FormGroup({
    title: new FormControl(this.title(), [Validators.required]), 
  })

  deleteColumn(){
      this.columnService.deleteColumn(this.id()).subscribe({
      next: () => this.dialogueRef.close(this.id()),
      error: (error:HttpErrorResponse) => this.showPopup(error.error.message)
    })
  }
  
  sendColumnToBackend(){
      if(this.id() != null){
        this.columnService.updateColumn(this.columnDetailsForm.value.title, this.id()).subscribe({
          next: (result: ColumnData) => {this.dialogueRef.close(result)},
          error: (error:HttpErrorResponse) => this.showPopup(error.error.message)
        })
          }
      else{
        this.columnService.addNewColumn(this.columnDetailsForm.value.title, this.boardId()).subscribe({
          next: (result: ColumnData) => {this.dialogueRef.close(result)},
          error: (error:HttpErrorResponse) => this.showPopup(error.error.message)
        })
      }
  }

  closeModal(){
      this.dialogueRef.close();
  }

  showPopup(error: string){
    this.snackBar.open(error, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }

}
