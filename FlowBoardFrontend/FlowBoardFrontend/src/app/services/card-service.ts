import { Injectable, signal } from '@angular/core';
import { CardData } from '../interfaces/card-data';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom, Observable, of } from 'rxjs';
import { ColumnService } from './column-service';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  private apiUrl = 'http://localhost:8080/api/cards'
  cards = signal<CardData[]>([]);
  constructor(private http:HttpClient){}

  async getCardsFromBackend(columnId: number){
    try{
        const data = await firstValueFrom(this.http.get<CardData[]>(this.apiUrl+'/' + columnId));
        this.cards.set([]);
        for(const item of data){
          this.cards.update(
            current  =>[...current, {
            id:item.id,
            dueDate: item.dueDate,
            title: item.title,
            positionInColumn: item.positionInColumn,
            description:item.description,
            assignees: item.assignees
          }])
        }
        this.cards().sort((a,b) => a.positionInColumn - b.positionInColumn);
    }catch(error){
      console.error(error);
    }
  }
  async moveCardInColumn(idOfFirstCard: number, whereWasItMoved: number){
      const request  = {
          idOfFirstObject: idOfFirstCard,
          positionOfSecondObject: whereWasItMoved
      }
      const data = await firstValueFrom(this.http.patch<CardData[]>(this.apiUrl + '/reorder', request));
      this.cards.set([]);
          for(const item of data){
            this.cards.update(
              current  =>[...current, {
              id:item.id,
              dueDate: item.dueDate,
              title: item.title,
              description:item.description,
              positionInColumn: item.positionInColumn,
              assignees: item.assignees
            }])
          }
      return this.cards().sort((a,b) => a.positionInColumn - b.positionInColumn);    
    }


    addNewCard(title: string, description: string ,dueDate: Date, columnId: number, assignees: string[]): Observable<CardData>{
      const localDate = [
        dueDate.getFullYear(),
        String(dueDate.getMonth() + 1).padStart(2,'0'),
        String(dueDate.getDate()).padStart(2, '0')
      ].join('-');

      const newCard = {title, description, dueDate: localDate, columnId, assignees};
      return this.http.post<CardData>(this.apiUrl + '/', newCard);
    }
    
    deleteCard(id: number): Observable<void>{
      return this.http.delete<void>(this.apiUrl+ '/' + id);
    }

    updateCard(id: number, title: string, description: string ,dueDate: Date, columnId: number, assignees: string[]): Observable<CardData>{
      const localDate = [
        dueDate.getFullYear(),
        String(dueDate.getMonth() + 1).padStart(2,'0'),
        String(dueDate.getDate()).padStart(2, '0')
      ].join('-');

      const updatedCard = {title, description, dueDate: localDate, columnId, assignees};
      return this.http.patch<CardData>(this.apiUrl + '/' + id, updatedCard);
    }
}
