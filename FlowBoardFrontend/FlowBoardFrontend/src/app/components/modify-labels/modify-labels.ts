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

@Component({
  selector: 'app-modify-labels',
  imports: [CommonModule,MatDialogModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  template: `
    <div class="card-detail-modal">
    <form [formGroup]="labelDetailsForm" (ngSubmit)="sendLabelRequestToBackend()">
    
    <div>
    <mat-form-field class="input-field">    
            <mat-label>Label name:</mat-label>
            <input matInput type=text id="username-input" formControlName="labelName"/>
            <mat-error
              *ngIf="showErrors && labelDetailsForm.get('labelName')?.hasError('required')">
              Name is required.
          </mat-error>
    </mat-form-field>

    <mat-form-field class="input-field">
      <mat-label>Choose color:</mat-label>
      <input matInput type="color" id="selected-color-input" formControlName="selectedColor"/>
      <mat-error
        *ngIf="showErrors && labelDetailsForm.get('selectedColor')?.hasError('required')">
        Color is required.
      </mat-error>
    </mat-form-field>

    </div>

    <ul class = label-list>
      @for (label of labels(); track label.name){
        <div class="label-element" [style.background-color]="label.color">
          <div class="name" >{{label.name}}</div>
          <button matButton="outlined" (click)=deleteLabel(label.name)>delete</button>
        </div>
      }
    </ul>


    
    
    
    <div class="button-row">
            <button matButton="outlined" class="button-accept"  type="submit">Confirm</button>
            <div button-row-second-part>
                <button matButton="outlined" class="button-cancel" type="button" (click)="closeModal()">cancel</button>
            </div>
    </div>

    </form>

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

  constructor(private boardService:BoardService){}
  labelDetailsForm = new FormGroup({
    labelName: new FormControl('', [Validators.required]),
    selectedColor: new FormControl('#6366ff', [Validators.required])
  })

  deleteLabel(labelName: string){
    this.showErrors = false;
    this.boardService.deleteLabel(this.boardId(), labelName).subscribe({
      next: () => {this.labels.update(list => list.filter(labels => labels.name !== labelName));},
      error: () => this.showPopup()
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
    error: () => this.showPopup()
  })
  }

  closeModal(){
      this.dialogueRef.close(this.labels());
  }

  showPopup(){
    const message = 'Unknown error appeared!';
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }
}
