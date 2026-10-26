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
  //also list of colabolators etc.
  done = signal<boolean>(this.data.done);

  assignedLabelNames(){
    return this.assignedLabels().map(label => label.name)
  }
  ngOnInit(){
    console.log("is done?");
    
    console.log(this.done());
    
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
    
    const newCard = this.cardService.addNewCard(this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees(), this.assignedLabels(),  this.cardDetailsForm.value.isDone!).subscribe({
      next: (result: CardData) => {
        this.dialogueRef.close(result)},
      error: () => this.showPopup()
    })
  }

  updateCard(){
    const updatedCard = this.cardService.updateCard(this.id(), this.cardDetailsForm.value.title, this.cardDetailsForm.value.description, this.cardDetailsForm.value.dueDate, this.columnId(), this.assignees(), this.assignedLabels(), this.cardDetailsForm.value.isDone!).subscribe({
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

  compareLabels(a: LabelData, b: LabelData): boolean{
   if(a && b && a.name === b.name){
      return true;
   }
   return false;
  }
}
