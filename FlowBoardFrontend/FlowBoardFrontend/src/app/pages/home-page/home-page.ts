import { Component, signal } from '@angular/core';
import { BoardService } from '../../services/board-service';
import { BoardSummary } from '../../interfaces/board-summary';
import { CardOfBoard } from '../../components/card-of-board/card-of-board';

@Component({
  selector: 'app-home-page',
  imports: [CardOfBoard],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss',
})
export class HomePage {
  constructor(private boardService:BoardService){}

  //boards: BoardSummary[] = [];
  boards = signal<BoardSummary[]>([]);
  joinedBoards = signal<BoardSummary[]>([]);
  async ngOnInit(){
    await this.boardService.getBoardsFromBackend();
    this.boards = this.boardService.boards;
    this.joinedBoards = this.boardService.joinedBoards;
  }
}
