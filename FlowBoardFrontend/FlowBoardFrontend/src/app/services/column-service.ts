import { Injectable, signal } from '@angular/core';
import {ColumnData} from '../interfaces/column-data'
import { BehaviorSubject, Observable, of, tap, firstValueFrom, from, map  } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CardData } from '../interfaces/card-data';
import { C } from '@angular/cdk/keycodes';
import { CardCreated, CardEdited, ColumnAdded, ColumnMoved } from '../interfaces/board-event';


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
    if(this.columns().find(column => column.id === columnData.id)){
      console.log("Column added previously by socket");
      
    }
    else{
      this.columns.update(columns => [...columns, columnData]);
    }
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
    if(this.columns().find(column => column.cards.find(tempCard => tempCard.id === card.id))){
      console.log("Card added earlier through socket");
      
    }
    else{
      this.columns.update(columns =>
        columns.map(column =>
          column.id === columnId ? { ...column, cards: [...column.cards, card] } : column
        )
      );
    }
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

  columnMoved(event: ColumnMoved){
    console.log("Column moved");
    
    console.log(event);
    
    const column = this.columns().find(column => column.id === event.columnId);
    if(column!.positionOnBoard > event.newPosition){
      this.columns().forEach(tempColumn => {
        if(tempColumn.positionOnBoard >= event.newPosition && tempColumn.positionOnBoard < column!.positionOnBoard){
          tempColumn.positionOnBoard += 1;
        }
      })
    }
    else if(column!.positionOnBoard < event.newPosition){
        this.columns().forEach(tempColumn => {
          if(tempColumn.positionOnBoard > column!.positionOnBoard && tempColumn.positionOnBoard <= event.newPosition){
            tempColumn.positionOnBoard -= 1;
          }
        })
    }
    column!.positionOnBoard = event.newPosition;
    this.columns.update(columns => [...columns].sort((a, b) => a.positionOnBoard - b.positionOnBoard));
  }

  
  columnRenamed(columnId: number, newName: string){
    const column = this.columns().find(column => column.id === columnId);
    column!.title = newName;
  }

  columnAdded(columnData: ColumnAdded){
    if(this.columns().find(column => column.id === columnData.columnId)){
      console.log("Column added previously")
    }
    else{
      let column : ColumnData = {} as ColumnData;
      column.id = columnData.columnId;
      column.positionOnBoard = columnData.columnPosition;
      column.title = columnData.title;
      column.cards = [];
    
      this.columns().forEach(tempColumn => {
        if(tempColumn.positionOnBoard >= column.positionOnBoard){
          tempColumn.positionOnBoard += 1;
        }
      })
      this.columns.update(columns => [...columns, column].sort((a,b) => a.positionOnBoard - b.positionOnBoard));
    }
  }

  columnDeleted(columnId: number){
    this.columns.update(columns => columns.filter(column => column.id !== columnId));
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

  moveCardToDifferentColumn(positionOnBoard: number, cardId: number, positionInColumn: number){    
    const targetColumn = this.columns().find(column => column.id === positionOnBoard);
    const sourceColumn = this.columns().find(column => column.cards.find(card => card.id === cardId));
    
    const card = sourceColumn?.cards.find(card => card.id === cardId);

    
  if (targetColumn && sourceColumn && card) {
      sourceColumn.cards = sourceColumn.cards.filter(
          card => card.id !== cardId
      );

      card.positionInColumn = positionInColumn;
      sourceColumn.cards.forEach(tempCard => {
        if(tempCard.positionInColumn >= card.positionInColumn){
          tempCard.positionInColumn -= 1;
        }
      })

      targetColumn.cards.forEach(tempCard => {
        if(tempCard.positionInColumn >= card.positionInColumn){
          tempCard.positionInColumn += 1;
        }
      })

      targetColumn.cards = [...targetColumn.cards, card].sort((a,b) => a.positionInColumn - b.positionInColumn);
      
  }
  }

  moveCardInTheSameColumn(newPosition: number, cardId: number){
    const column = this.columns().find(column => column.cards.find(card => card.id === cardId));
    
    const card = column?.cards.find(card => card.id === cardId);
    
    if(newPosition > card!.positionInColumn){
    column?.cards.forEach(tempCard => {
      if(tempCard.positionInColumn > card!.positionInColumn && tempCard.positionInColumn <= newPosition){
        tempCard.positionInColumn -= 1;
      }

    })
  }
  else{
      column?.cards.forEach(tempCard => {
      if(tempCard.positionInColumn < card!.positionInColumn && tempCard.positionInColumn >= newPosition){
        tempCard.positionInColumn += 1;
      }
    })
  }

    card!.positionInColumn = newPosition;
    column!.cards = column!.cards.sort((a,b) => a.positionInColumn - b.positionInColumn);
    console.log(column);
  }


    newCardCreated(event: CardCreated){
        console.log("Card created");
        console.log(event);
        if(this.columns().find(column => column.cards.find(card => card.id === event.payload.cardId))){
          console.log("Card exist, shouldn't duplicate it");
        }
        else{
          let card: CardData = {} as CardData;
          card.assignees = event.payload.assignees;
          card.description = event.payload.description;
          card.done = event.payload.isDone;
          card.dueDate = event.payload.dueDate;
          card.id = event.payload.cardId;
          card.labels = event.payload.labels;
          card.positionInColumn = event.payload.positionInColumn;
          card.title = event.payload.title;

          const column = this.columns().find(column => column.id === event.payload.columnId);
          column?.cards.forEach(tempCard => {
            if(tempCard.positionInColumn >= card.positionInColumn){
              tempCard.positionInColumn += 1;
            }
          });

        column!.cards = [...column!.cards, card].sort((a,b) => a.positionInColumn - b.positionInColumn);
      console.log(column);  
      }
       
       
    }
    
  
    cardEdited(event: CardEdited){
      console.log("Card edited");
      console.log(event);
      

      const card = this.columns().flatMap(tempColumn => tempColumn.cards).find(card => card.id === event.payload.cardId);
      card!.assignees = event.payload.assignees;
      card!.description = event.payload.description;
      card!.done = event.payload.isDone;
      card!.dueDate = event.payload.dueDate;
      card!.labels = event.payload.labels;
      card!.positionInColumn = event.payload.positionInColumn;
      card!.title = event.payload.title;

    }

  
    cardDeleted(cardId: number){
      console.log("Card deleted");
      
      const column = this.columns().find(tempColumn => tempColumn.cards.find(card => card.id === cardId));
      
      const cardPosition = column!.cards.find(card => card.id === cardId)!.positionInColumn;
      column!.cards = column!.cards.filter(
          card => card.id !== cardId
      );

      column!.cards.forEach(tempCard => {
        if(tempCard.positionInColumn >= cardPosition){
          tempCard.positionInColumn -= 1;
        }
      })
      console.log(column);
      
    }

}
