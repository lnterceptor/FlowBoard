import { Component, input } from '@angular/core';
import { CardData } from '../../interfaces/card-data';
import { Card } from '../card/card';
import { from } from 'rxjs';
import { AddNewCard } from '../add-new-card/add-new-card';
import{
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
  transferArrayItem
} from '@angular/cdk/drag-drop'
import { CardService } from '../../services/card-service';
import { ColumnService } from '../../services/column-service';
import { MatDialog } from '@angular/material/dialog';
import { ColumnDetailModalComponent } from '../column-detail-modal-component/column-detail-modal-component';
import { ColumnData } from '../../interfaces/column-data';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-column',
  imports: [Card, DragDropModule, AddNewCard, MatIcon, MatButtonModule],
  template: `
  <div class="column">
  <div  class="title-bar">  
    <span class = "title">{{title()}} </span>
    <button mat-icon-button aria-label="Settings" class="settings-button" (click)="updateColumn()"> <mat-icon>settings</mat-icon> </button>
</div >
  <ul
      cdkDropList
      cdkDropListOrientation="vertical"
      [cdkDropListData]="cards()"
      (cdkDropListDropped)="drop($event)"
    >
    @for (card of cards(); track card.positionInColumn) {
        <app-card 
          cdkDrag
          [id]="card.id"
          [positionInColumn]="card.positionInColumn"
          [description]="card.description"
          [title]="card.title"
          [columnId]="this.id()"
          [dueDate]="this.dueDate"
        />
    }
    <app-add-new-card [columnId]="this.id()"></app-add-new-card>
    </ul>
  </div>
  `,
  styleUrl: './column.scss',
})
export class Column {
  id = input<number>();
  title = input<string>();
  position_on_board = input<number>();
  cards = input<CardData[]>([]);
  dueDate: Date = new Date();
  boardId = input<number>();

  constructor(private dialog: MatDialog, private cardService: CardService, private columnService: ColumnService){};
  
  updateColumn(){
    const dialogRef = this.dialog.open(ColumnDetailModalComponent, {
            disableClose: true,
            data:{boardId: this.boardId(), id: this.id(), title:this.title()}
          });
          dialogRef.afterClosed().subscribe((column: ColumnData | number | undefined) =>{
            if(!column){
              return;
            }
            if(typeof column === 'number'){
              this.columnService.deleteColumnInColumnList(column)
            }
            else{
                this.columnService.updateColumnInColumnList(column, this.id()!);
            }
          });
  }

  drop(event: CdkDragDrop<CardData[]>) {
    if (event.previousContainer === event.container){
    moveItemInArray(
      this.cards(),
      event.previousIndex,
      event.currentIndex
    );
    const movedCard = this.cards()[event.currentIndex];
    from(this.cardService.moveCardInColumn(movedCard.id, event.currentIndex)).subscribe(
          {
            next: () => {
            },
            error: (error) => {
              console.error(error);
            }
          }
    );

  }
  else{
    transferArrayItem(
      event.previousContainer.data,
      this.cards(),
      event.previousIndex,
      event.currentIndex
    );
    const movedCard = this.cards()[event.currentIndex];

    const currentIndex: number = event.currentIndex;
    from(this.columnService.moveCardBetweenColumns(movedCard.id, this.id()!,currentIndex))
    .subscribe();
  }
  }

}
