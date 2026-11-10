import { Component, input, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BoardsDetailModalComponent } from '../boards-detail-modal-component/boards-detail-modal-component';
import { BoardSummary } from '../../interfaces/board-summary';
import { BoardService } from '../../services/board-service';

@Component({
  selector: 'app-add-new-board-card',
  imports: [],
  template: `
    <div class="boardCard" (click)="onItemClick()">
      <div class="content">
        <p class = "title"> {{title()}}</p>
      </div>
    </div>
  `,
  styleUrl: './add-new-board-card.scss',
})
export class AddNewBoardCard {
  title = input<string>();
  router = inject(Router);

  constructor(private dialog: MatDialog, private boardService: BoardService){};

  onItemClick(){
    const dialogRef = this.dialog.open(BoardsDetailModalComponent, {
          disableClose: true,
          data:{}
        });
        dialogRef.afterClosed().subscribe((board: BoardSummary) =>{
          if(!board){
            return;
          }
            this.boardService.addNewBoardToUserCollection(board);
          });
    }
}
