import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { BoardService } from '../../services/board-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modify-collaborator',
  imports: [CommonModule,MatDialogModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
  template: `
    <div class="card-detail-modal">
    <form [formGroup]="collaboratorDetailsForm" (ngSubmit)="sendCollaboratorRequestToBackend()">
        <mat-form-field class="input-field">    
            <mat-label>Username:</mat-label>
            <input matInput type=text id="username-input" formControlName="username"/>
            
            <mat-error
              *ngIf="showErrors && collaboratorDetailsForm.get('username')?.hasError('required')">
              Username is required.
          </mat-error> 
    </mat-form-field>

    <ul class = collabolator-list>
      @for (collaborator of collaborators(); track collaborator){
        <div class="collaborator-element">
          <div class="username">{{collaborator}}</div>
          <button matButton="outlined" (click)=deleteCollaborator(collaborator)>delete</button>
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
  styleUrl: './modify-collaborator.scss',
})
export class ModifyCollaborator {
  data = inject(MAT_DIALOG_DATA);
  boardId = signal(this.data.boardId);
  collaborators = signal<string[]>(this.data.collaborators);
  private dialogueRef = inject(MatDialogRef);
  showErrors = false;

  constructor(private boardService:BoardService){}
  collaboratorDetailsForm = new FormGroup({
    username: new FormControl('', [Validators.required]), 
  })

  deleteCollaborator(username: string){
    this.showErrors = false;
    this.boardService.deleteCollaborator(username, this.boardId()).subscribe({
      next: () => {this.collaborators.update(list => list.filter(collaborator => collaborator !== username));},
      error: () => this.showPopup()
    })
  }
  
  sendCollaboratorRequestToBackend(){
    if(this.collaboratorDetailsForm.invalid){
      this.showErrors = true;
      return;
    }
    this.showErrors = false;

  this.boardService.addCollaborator(this.collaboratorDetailsForm.value.username!, this.boardId()).subscribe({
    next: (collaborators: string[]) => {this.collaborators.set(collaborators)},
    error: () => this.showPopup()
  })
  }

  closeModal(){
      this.dialogueRef.close(this.collaborators());
  }

  showPopup(){
    const message = 'Unknown error appeared!';
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }
}
