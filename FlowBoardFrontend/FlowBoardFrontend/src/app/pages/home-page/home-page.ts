import { Component, inject, signal } from '@angular/core';
import { BoardService } from '../../services/board-service';
import { BoardSummary } from '../../interfaces/board-summary';
import { CardOfBoard } from '../../components/card-of-board/card-of-board';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { AddNewBoardCard } from '../../components/add-new-board-card/add-new-board-card';

@Component({
  selector: 'app-home-page',
  imports: [CardOfBoard, MatButtonModule, MatIcon, AddNewBoardCard],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss',
})
export class HomePage {
  constructor(private boardService:BoardService, private authService: AuthService){}
  router = inject(Router);
  boards = signal<BoardSummary[]>([]);
  joinedBoards = signal<BoardSummary[]>([]);
  menuOpen = false;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  async ngOnInit(){
    await this.boardService.getBoardsFromBackend();
    this.boards = this.boardService.boards;
    this.joinedBoards = this.boardService.joinedBoards;
  }
  logout(){
    this.authService.logout();
    this.router.navigateByUrl('/sign-in');
  }

  checkProfile(){
    this.router.navigateByUrl('/profile');
  }

  
}
