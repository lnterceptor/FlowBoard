import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { User } from '../../interfaces/user';
import { UserService } from '../../services/user-service';
import { MatDialog } from '@angular/material/dialog';
import { PasswordChange } from '../../components/password-change/password-change';
import { FormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';


@Component({
  selector: 'app-profile',
  imports: [ReactiveFormsModule, FormsModule, MatIcon],
  templateUrl: './profile.html',
  styleUrl: './profile.scss',
})
export class Profile {
  authService = inject(AuthService);
  router = inject(Router);
  isEditingUsername : boolean = false;
  isEditingEmail: boolean = false;
  user : User | null = null;
  editedUsername: string = '';
  editedEmail: string = '';

  constructor(private dialog: MatDialog, private http:HttpClient, private userService:UserService){
      this.authService.currentUser.subscribe(
        user => {
          this.user = user
          this.editedEmail = this.user!.email;
          this.editedUsername = this.user!.username;
      }
      )
  }
  saveChanges(){
    this.userService.updateUserData(this.editedEmail, this.editedUsername, this.user?.userId!).subscribe({
      next: (result) => {
        this.authService.setUser(result);
      },
      error: () => {
        this.editedEmail = this.user!.email!;
        this.editedUsername = this.user!.username!;
        this.showPopup()
      }
    })
  }

  editPassword(){
    const dialogRef = this.dialog.open(PasswordChange, {
        width: '60vw',
        height: '65vh',
        maxWidth: '60vw',
        maxHeight: '65vh',
        data:{userId: this.user!.userId!}
      });
  }
  
  deleteAccount(){
    if(confirm("Are you sure that you want delete your account and all associated data?")){
      this.userService.deleteAccount(this.user?.userId!).subscribe({
        next: () => {
              this.authService.logout();
              this.router.navigateByUrl('/sign-in');
        }
      });
    }
    
    
  }
  
  showPopup(){
    const message = 'Unknown error appeared!';
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }

  goToMainPage(){
    this.router.navigateByUrl(`/`);
  }

}