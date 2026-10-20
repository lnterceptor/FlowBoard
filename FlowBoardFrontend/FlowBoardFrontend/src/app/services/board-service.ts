import { Injectable, signal } from '@angular/core';
import { BoardSummary } from '../interfaces/board-summary';
import { BehaviorSubject, Observable, of, tap, firstValueFrom, from, map  } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class BoardService {
  private apiUrl = 'http://localhost:8080/api/boards'
  boards = signal<BoardSummary[]>([]);
  
  private userId = 5; //replace with actual userId after connecting user to frontend

  constructor(private http:HttpClient){}

  async getBoardsFromBackend(){
    try{
      const data = await firstValueFrom(this.http.get<BoardSummary[]>(this.apiUrl+'/' + this.userId));
      this.boards.set([]);
      for(const item of data){
            this.boards.update(
              current  =>[...current, {
              id:item.id,
              title:item.title,
              description: item.description
            }])
    }
  }catch(error){
    console.error('Failed retrieving user boards.');
    console.error(error);
  }}
  
  deleteBoard(boardId: number): Observable<void>{
    return this.http.delete<void>(this.apiUrl+'/'+ boardId);
  }
  updateBoard(title: string, desc: string, boardId: number): Observable<BoardSummary>{
    const dataToSend = {name: title, desc , userId: this.userId};
    return this.http.patch<BoardSummary>(this.apiUrl+'/'+ boardId, dataToSend);
  }
  createNewBoard(title: string, desc: string): Observable<BoardSummary>{
    const dataToSend = {name: title, desc , userId: this.userId};
    return this.http.post<BoardSummary>(this.apiUrl+'/', dataToSend);
  }

  addNewBoardToUserCollection(board: BoardSummary){
    this.boards.update(boards => [...boards, {id: board.id, title: board.title, description: board.description}]);
  }
  updateBoardInUserCollection(board: BoardSummary){
    this.boards.update(boards => boards.map(boardToCheck => boardToCheck.id === board.id ? board: boardToCheck));
    
  }

  getBoardDataFromBackend(boardId: number): Observable<BoardSummary>{
    return this.http.get<BoardSummary>(this.apiUrl+'/board/'+boardId);
  }
}
