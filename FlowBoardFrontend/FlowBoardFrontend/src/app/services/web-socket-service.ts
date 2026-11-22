import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IMessage, RxStomp } from '@stomp/rx-stomp';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private rxStomp = new RxStomp();
  constructor(){
    this.rxStomp.configure({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,

      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    })

    
    this.rxStomp.activate();
  }

  watchBoard(boardId: number): Observable<IMessage>{
      return this.rxStomp.watch(`/topic/boards/${boardId}`);
  }

  watchCard(boardId: number, cardId: number): Observable<IMessage>{
    return this.rxStomp.watch(`/topic/boards/${boardId}/cards/${cardId}`)
  }

  disconnect(){
    this.rxStomp.deactivate();
  }


}
