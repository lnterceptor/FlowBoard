import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BoardService } from '../../services/board-service';
import { CommonModule } from '@angular/common';
import { LabelData } from '../../interfaces/label-data';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-modify-labels',
  imports: [CommonModule,MatDialogModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  template: `
    <div class="card-detail-modal">
  <form [formGroup]="labelDetailsForm" class="boardForm" (ngSubmit)="sendLabelRequestToBackend()">
    
    <div>
    <mat-form-field class="input-field">    
            <mat-label>Label name:</mat-label>
            <input matInput type=text id="label-input" formControlName="labelName"/>
            <mat-error
              *ngIf="showErrors && labelDetailsForm.get('labelName')?.hasError('required')">
              Label name is required.
          </mat-error>
    </mat-form-field>

    <mat-form-field class="input-field">
      <mat-label>Choose color:</mat-label>
      <input matInput type="color" id="selected-color-input" formControlName="selectedColor"/>
    </mat-form-field>

    </div>
    </form>

    <ul class = "label-list">
      @for (label of labels(); track label.name){
        <div class="label-element" [style.background-color]="label.color">
          <div class="name" >{{label.name}}</div>
          <button matButton="outlined" class="button-delete"(click)=deleteLabel(label.name)>delete</button>
          
        </div>
      }
    </ul>


    <div class="button-row">
        <button matButton="outlined" class="button-accept" type="button" (click)="sendLabelRequestToBackend()">Add label</button>
        <button matButton="outlined" class="button-cancel" type="button" (click)="closeModal()">cancel</button>
    </div>

    

</div>
  `,
  styleUrl: './modify-labels.scss',
})

export class ModifyLabels {
  data = inject(MAT_DIALOG_DATA);
  boardId = signal(this.data.boardId);
  labels = signal<LabelData[]>(this.data.labels);
  private dialogueRef = inject(MatDialogRef);
  showErrors = false;

  constructor(private boardService:BoardService, public snackBar: MatSnackBar ){}

  labelDetailsForm = new FormGroup({
    labelName: new FormControl('', [Validators.required]),
    selectedColor: new FormControl('#6366ff', [Validators.required])
  })

  deleteLabel(labelName: string){
    this.labelDetailsForm.get('labelName')?.setValue('');
    this.showErrors = false;
    this.boardService.deleteLabel(this.boardId(), labelName).subscribe({
      next: () => {this.labels.update(list => list.filter(labels => labels.name !== labelName));},
      error: (error: HttpErrorResponse) => this.showPopup(error.error.message)
    })
  }
  
  sendLabelRequestToBackend(){
    if(this.labelDetailsForm.invalid){
      this.showErrors = true;
      return;
    }
    this.showErrors = false;

  this.boardService.addLabel(this.boardId(), this.labelDetailsForm.value.labelName!, this.labelDetailsForm.value.selectedColor!).subscribe({
    next: (label: LabelData) => {this.labels.update(labels =>[...labels, label])},
    error: (error: HttpErrorResponse) => this.showPopup(error.error.message)
  })
  }

  closeModal(){
      this.dialogueRef.close(this.labels());
  }

  showPopup(error: string){
    this.snackBar.open(error, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }
}
