import { Component, input, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { BoardsDetailModalComponent } from '../boards-detail-modal-component/boards-detail-modal-component';
import { BoardSummary } from '../../interfaces/board-summary';
import { BoardService } from '../../services/board-service';

@Component({
  selector: 'app-card-of-board',
  imports: [],
  template: `
    <div class="boardCard" (click)="onItemClick()">
      <p class = "title"> title: {{title()}}</p>
      <p class = "description">  desc: {{description()}}</p>
      <p class = "description">  id: {{id()}} </p>
    </div>
  `,
  styleUrl: './card-of-board.scss',
})
export class CardOfBoard {
  id = input<number>();
  title = input<string>();
  description = input<string>();
  router = inject(Router);

  constructor(private dialog: MatDialog, private boardService: BoardService){};

  onItemClick(){
    if(this.id() != null){
    this.router.navigateByUrl(`/board/${this.id()}`);
  }
    else{
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
}
