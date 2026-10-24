import { Component, input } from '@angular/core';
import { CardData } from '../../interfaces/card-data';
import { MatDialog } from '@angular/material/dialog';
import { CardDetailModalComponent } from '../card-detail-modal-component/card-detail-modal-component';
import { ColumnService } from '../../services/column-service';
import { LabelData } from '../../interfaces/label-data';

@Component({
  selector: 'app-card',
  imports: [],
  template: `
    <div class="card">
    <p class="title">{{title()}}</p>
    <p class="description">  {{description()}} id of card is {{id()}} </p>
    <ul>
      @for(username of assignees(); track $index){
       <div class = "description">{{ username }}</div>
      }
    </ul>
    <ul>
      @for(label of assignedLabels(); track label.name){
        <div class = "description" [style.color]="label.color">{{label.name}}</div>
      }
    </ul>

    <button mat-icon-button (click)="openCardDetails(); $event.stopPropagation()">
    Details, change to icon later
    </button>
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
  title = input<string>();
  assignees = input<string[]>();
  collaborators = input<string[]>();
  avaibleLabels = input<LabelData[]>();
  assignedLabels = input<LabelData[]>();

  constructor(private dialog: MatDialog, private columnService: ColumnService) {}

  openCardDetails(){
    const dialogRef = this.dialog.open(CardDetailModalComponent, {
    disableClose: true,
    data:{id: this.id(), dueDate: this.dueDate(), description: this.description(), title: this.title(), columnId: this.columnId, assignees: this.assignees(), collaborators: this.collaborators(), avaibleLabels: this.avaibleLabels(), assignedLabels: this.assignedLabels()}
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
  //const dialogRef = this.dialog.open(AddTransaction, { disableClose: true,
  //      data:{id:id, amount:transaction.amount, category:transaction.category, date:transaction.date_of_transaction, description:transaction.description}
  //  });
  }
}
