import { Component, inject, input, signal } from '@angular/core';
import { UserService } from '../../services/user-service';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-password-change',
  imports: [FormsModule, MatFormFieldModule],
  template: `
    
    <div class = "modal-detail">
      <div class="title">Change password</div>
      <div class = "inputs">
      <mat-form-field class="input-field">
                <div class="text-over-input">Current password</div>
                <mat-label for="currentPassword" class="label-text">Current password:</mat-label>
                <input type="password"
                        id = "currentPassword"
                        placeholder="Current password"
                        [(ngModel)]="currentPassword"
                />
        </mat-form-field>
        <mat-form-field class="input-field">
          <div class="text-over-input">New password</div>
                <mat-label for="newPassword" class="label-text">New password:</mat-label>
                <input type="password"
                        id = "newPassword"
                        placeholder="New password"
                        [(ngModel)]="newPassword"
                />
        </mat-form-field>
        <mat-form-field class="input-field">
          <div class="text-over-input">Repeat new password</div>
                <mat-label for="repeatNewPassword" class="label-text">Repeat new password:</mat-label>
                <input type="password"
                        id = "repeatNewPassword"
                        placeholder="Repeat new password"
                        [(ngModel)]="repeatNewPassword"
                />
        </mat-form-field>
      </div>
      <button matButton="outlined" class="input-button" type="button" (click)="changePassword()">Change Password</button>

    </div>
  `,
  styleUrl: './password-change.scss',
})
export class PasswordChange {
  constructor(private userService:UserService){}
  data = inject(MAT_DIALOG_DATA);
  repeatNewPassword: string = '';
  newPassword: string = '';
  currentPassword: string = '';
  userId = signal(this.data.userId);
  private dialogueRef = inject(MatDialogRef);

  changePassword(){
   
    if(this.newPassword === this.repeatNewPassword && this.newPassword.length >= 5){
      this.userService.updatePassword(this.currentPassword, this.newPassword, this.userId()!).subscribe({
      next: () => {this.dialogueRef.close();},
      error: (error) => this.showPopup(error)
    })
    }
    else{
       console.log("error occured while changing ")
    }
  }

  showPopup(error: string){
    const message = 'Unknown error appeared!';
    console.log(error)
    console.log(message);
    
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }

}
