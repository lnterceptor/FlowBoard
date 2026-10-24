import { Component, input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CardDetailModalComponent } from '../card-detail-modal-component/card-detail-modal-component';
import { ColumnService } from '../../services/column-service';
import { CardData } from '../../interfaces/card-data';

@Component({
  selector: 'app-add-new-card',
  imports: [],
  template: `
    <div class="card" (click)="openCardDetails()">
      <p class = "title"> add new card</p>
    </div>
  `,
  styleUrl: './add-new-card.scss',
})
export class AddNewCard {
  constructor(private dialog: MatDialog, private columnService: ColumnService) {}
  columnId = input<number>();
  collaborators = input<string[]>();

  openCardDetails(){
        const dialogRef = this.dialog.open(CardDetailModalComponent, {
        disableClose: true,
        data:{description: '', title: '', columnId: this.columnId(), collaborators: this.collaborators()}
      });
      dialogRef.afterClosed().subscribe((card: CardData) =>{
    if(card) {
      this.columnService.addNewCardToColumn(card, this.columnId()!);
    }
  })
  }
}
