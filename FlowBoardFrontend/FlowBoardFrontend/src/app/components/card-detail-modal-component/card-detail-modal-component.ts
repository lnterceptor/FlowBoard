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

@Component({
  selector: 'app-card-detail-modal-component',
  imports: [CommonModule, FormsModule, MatChipsModule, MatIconModule, MatNativeDateModule, MatDialogModule, MatDatepickerModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatSelectModule],
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
  //also list of colabolators etc.

  ngOnInit(){
    console.log("List of collaborators");
    
    console.log(this.collaborators());
    
  }


  cardDetailsForm = new FormGroup({
    title: new FormControl(this.title(), [Validators.required]),
    dueDate: new FormControl(this.dueDate(), [Validators.required]),
    description: new FormControl(this.description())    
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
    console.log("assignees for created new card!");
    
    console.log(this.assignees());
    
    const newCard = this.cardService.addNewCard(this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees()).subscribe({
      next: (result: CardData) => {
        this.dialogueRef.close(result)},
      error: () => this.showPopup()
    })
  }

  updateCard(){
    const updatedCard = this.cardService.updateCard(this.id(), this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees()).subscribe({
      next: (result: CardData) => {this.dialogueRef.close(result)},
      error: () => this.showPopup()
    })
  }

  showPopup(){
    const message = 'Unknown error appeared!';
    //this.snackBar.open(message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
    return true;
  }

  closeModal(){
    this.dialogueRef.close();
  }

  deleteCard(){
    this.cardService.deleteCard(this.id()).subscribe({
      next: () => this.dialogueRef.close(this.id()),
      error: () => this.showPopup()
    })
  }
}
