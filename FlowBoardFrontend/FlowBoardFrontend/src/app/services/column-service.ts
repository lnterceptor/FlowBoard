import { Injectable, signal } from '@angular/core';
import {ColumnData} from '../interfaces/column-data'
import { BehaviorSubject, Observable, of, tap, firstValueFrom, from, map  } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CardData } from '../interfaces/card-data';


@Injectable({
  providedIn: 'root',
})
export class ColumnService {
  private apiUrl = 'http://localhost:8080/api/columns'
  columns = signal<ColumnData[]>([]);
  constructor(private http:HttpClient){}

  
  async getColumnsFromBackend(boardId: number){
    try{
      
    const data = await firstValueFrom(this.http.get<ColumnData[]>(this.apiUrl+'/' + boardId));
    this.columns.set([]);
    for(const item of data){
      this.columns.update(
        current  =>[...current, {
        id:item.id,
        title:item.title,
        positionOnBoard: item.positionOnBoard,
        cards: item.cards.sort((a,b) => a.positionInColumn - b.positionInColumn)
      }])
    }
    this.columns().sort((a,b) => a.positionOnBoard - b.positionOnBoard);
    
  } catch(error) {
      console.error('Failed retrieving columns.');
      console.error('REQUEST FAILED:', error);
    }
  }
  
  addNewColumn(title: string, boardId: number): Observable<ColumnData>{
    const newColumn = {title: title, boardId: boardId}
    return this.http.post<ColumnData>(this.apiUrl + '/', newColumn);
  }
  addNewColumnToColumnList(columnData: ColumnData){
    this.columns.update(columns => [...columns, columnData]);
  }

  updateColumnInColumnList(columnData: ColumnData, columnIndex: number){
    this.columns.update(columns =>
      columns.map(column => column.id === columnIndex ? columnData : column)
    );
  }
  deleteColumnInColumnList(columnIndex: number){
    this.columns.update(columns =>
      columns.filter(column => column.id !== columnIndex)
    );
  }


  updateColumn(columnName: string, columnId:number): Observable<ColumnData>{
    
    return this.http.patch<ColumnData>(this.apiUrl + '/' + columnId, columnName);
  }
  deleteColumn(columnId: number): Observable<void>{
    return this.http.delete<void>(this.apiUrl + '/' + columnId);
  }

  addNewCardToColumn(card: CardData, columnId: number){
    this.columns.update(columns =>
      columns.map(column =>
        column.id === columnId ? { ...column, cards: [...column.cards, card] } : column
      )
    );
  }
  

  changeCardInColumn(card: CardData, columnId: number, cardId: number){
    this.columns.update(columns =>
      columns.map(column =>
        column.id === columnId ? 
        { ...column, cards: column.cards.map(c => c.id === cardId ? card : c) } : column
      )
    );

  }

  removeCard(columnId: number, cardId: number){
    this.columns.update(columns =>
      columns.map(column =>
        column.id === columnId ? 
        { ...column, cards: column.cards.filter(c => c.id !== cardId) } : column
      )
    );
  }

  async moveColumn(idOfFirstColumn: number, whereWasItMoved: number){
    const request  = {
        idOfFirstObject: idOfFirstColumn,
        positionOfSecondObject: whereWasItMoved
    }
    const data = await firstValueFrom(this.http.patch<ColumnData[]>(this.apiUrl + '/reorder', request));
    this.columns.set([]);
        for(const item of data){
          this.columns.update(
            current  =>[...current, {
            id:item.id,
            title:item.title,
            positionOnBoard: item.positionOnBoard,
            cards: item.cards.sort((a,b) => a.positionInColumn - b.positionInColumn)
          }])
        }
    return this.columns().sort((a,b) => a.positionOnBoard - b.positionOnBoard);    
  }

  
  async moveCardBetweenColumns(idOfFirstCard: number, newColumnId: number, placementInNewColumn: number){
      const request  = {
          cardId: idOfFirstCard,
          newColumnId: newColumnId,
          placementInNewColumn: placementInNewColumn
      }
      
      const data = await firstValueFrom(this.http.patch<ColumnData[]>('http://localhost:8080/api/cards/moveCard', request));
      this.columns.set([]);
      for(const item of data){
            this.columns.update(
              current  =>[...current, {
              id:item.id,
              title:item.title,
              positionOnBoard: item.positionOnBoard,
              cards: item.cards.sort((a,b) => a.positionInColumn - b.positionInColumn)
            }])
          }
    this.columns().sort((a,b) => a.positionOnBoard - b.positionOnBoard);    
  }

}
