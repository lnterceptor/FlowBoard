import { Component, input } from '@angular/core';
import { CardData } from '../../interfaces/card-data';
import { MatDialog } from '@angular/material/dialog';
import { CardDetailModalComponent } from '../card-detail-modal-component/card-detail-modal-component';
import { ColumnService } from '../../services/column-service';
import { LabelData } from '../../interfaces/label-data';
import { NgClass } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-card',
  imports: [NgClass, MatIcon, MatChipsModule],
  template: `
    <div class="card" [ngClass]="getDateStatus()">
    <div class="title-paragraph">
      <div class="title">{{title()}}</div>
      <button mat-icon-button aria-label="Settings" class="settings-button" (click)="openCardDetails(); $event.stopPropagation()"><mat-icon>settings</mat-icon> </button>
    </div>
    <p class="description">  {{description()}} </p>
    
    <div class="chips">
      <mat-chip-set aria-label="Selected labels">
        @for (label of assignedLabels(); track label.name) {
          <mat-chip [style.background-color]="label.color">
            {{ label.name }}
          </mat-chip>
        }
      </mat-chip-set>
    </div> 

    <div class="assignees">
      @for(username of assignees(); track $index; let last = $last){
       <div class = "assignee">{{ username }} @if(!last){,}</div>
      }
    </div>
    
    </div>
  `,
  styleUrl: './card.scss',
})
export class Card {
  id = input<number>();
  description = input<string>();
  positionInColumn = input<number>();
  columnId = input<number>();
  dueDate = input<Date>();
  done = input<boolean>();
  title = input<string>();
  assignees = input<string[]>();
  collaborators = input<string[]>();
  avaibleLabels = input<LabelData[]>();
  assignedLabels = input<LabelData[]>();
  boardId = input<number>();

  constructor(private dialog: MatDialog, private columnService: ColumnService) {}

  openCardDetails(){
    const dialogRef = this.dialog.open(CardDetailModalComponent, {
    width: '80vw',
    height: '75vh',
    maxWidth: '80vw',
    maxHeight: '75vh',
    disableClose: true,
    data:{id: this.id(), dueDate: this.dueDate(), done: this.done(), description: this.description(), title: this.title(), columnId: this.columnId, assignees: this.assignees(), collaborators: this.collaborators(), avaibleLabels: this.avaibleLabels(), assignedLabels: this.assignedLabels(), boardId: this.boardId()}
  });
  dialogRef.afterClosed().subscribe((card: CardData | number | undefined) =>{
    if(!card){
      return;
    }
    if (typeof card === 'number') {
      this.columnService.removeCard(this.columnId()!, card);
    } 
    else {
      this.columnService.changeCardInColumn(card, this.columnId()!, this.id()!);
    }
 
  })
  }

  
  getDateStatus(){    
    if(this.done()){
      return 'done'
    }
    const now = new Date();
    
    const today = [
      now.getFullYear(),
      String(now.getMonth() + 1).padStart(2,"0"),
      String(now.getDate()).padStart(2,"0"),
    ].join("-");
    
    
    if(today > this.dueDate()!.toString()){
      return 'overdue';
    }
    else if(today == this.dueDate()!.toString()){
      return 'due-soon';
    }
    else{
      return 'upcoming'
    }
  }

}
