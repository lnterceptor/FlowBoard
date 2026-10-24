import { Component, inject, input, signal } from '@angular/core';
import { Column } from '../../components/column/column';
import { AddNewCard } from '../../components/add-new-card/add-new-card';
import { AsyncPipe } from '@angular/common';
import { ColumnService } from '../../services/column-service';
import { ColumnData } from '../../interfaces/column-data';
import { from } from 'rxjs';
import{
  CdkDragDrop,
  DragDropModule,
  moveItemInArray
} from '@angular/cdk/drag-drop'
import { ActivatedRoute, Router } from '@angular/router';
import { ColumnDetailModalComponent } from '../../components/column-detail-modal-component/column-detail-modal-component';
import { MatDialog } from '@angular/material/dialog';
import { BoardsDetailModalComponent } from '../../components/boards-detail-modal-component/boards-detail-modal-component';
import { BoardSummary } from '../../interfaces/board-summary';
import { BoardService } from '../../services/board-service';
import { ModifyCollaborator } from '../../components/modify-collaborator/modify-collaborator';

@Component({
  selector: 'app-board',
  imports: [Column, DragDropModule],
  templateUrl: './board.html',
  styleUrl: './board.scss',
})
export class Board {
  columns = signal<ColumnData[]>([]);
  id!: number;
  router = inject(Router);
  title: string = "";
  description:string = "";
  collaborators: string[] = [];

  constructor(private dialog: MatDialog, private columnService:ColumnService, private route: ActivatedRoute, private boardService: BoardService){}

  async ngOnInit(){
    const idParam = this.route.snapshot.paramMap.get('id');
    this.id = idParam ? + idParam : 0;
    await this.columnService.getColumnsFromBackend(this.id);
    this.boardService.getBoardDataFromBackend(this.id).subscribe({
      next: (board: BoardSummary) =>{
        this.title = board.title;
        this.description = board.description;
        this.collaborators = board.collaborators;
        this.columns = this.columnService.columns;
      }}
    );
    
    
    

  }
  drop(event: CdkDragDrop<ColumnData[]>) {
    moveItemInArray(
      this.columns(),
      event.previousIndex,
      event.currentIndex
    );

    const movedColumn = this.columns()[event.currentIndex];
    from(this.columnService.moveColumn(movedColumn.id, event.currentIndex)).subscribe(
      {
        next: () => {
        },
        error: (error) => {
          console.log(error);
        }
      }
    );
  }

  addNewColumn(){
    const dialogRef = this.dialog.open(ColumnDetailModalComponent, {
        disableClose: true,
        data:{boardId: this.id}
      });
      dialogRef.afterClosed().subscribe((column: ColumnData) =>{
        if(!column){
          return;
        }
          this.columnService.addNewColumnToColumnList(column);
        
      });
  }

  modifyCollabolators(){
    const dialogRef = this.dialog.open(ModifyCollaborator, {
      width: '60vw',
      height: '60vh',
      disableClose: true,
      data:{boardId: this.id, collaborators: this.collaborators}
    })
    dialogRef.afterClosed().subscribe((collaborators: string[]) =>
    this.collaborators = collaborators)
  }

  updateBoard(){
    const dialogRef = this.dialog.open(BoardsDetailModalComponent, {
      disableClose: true,
      data:{id: this.id, title: this.title, description: this.description}
    });
    dialogRef.afterClosed().subscribe((board: BoardSummary) =>{
    if(!board){
      return;
    }
      this.title = board.title;
      this.description = board.description;
      this.boardService.updateBoardInUserCollection(board);
    });
  }
  goToMainPage(){
    this.router.navigateByUrl(`/`);
  }

  deleteBoard(){
      const confirmed = window.confirm("Are you sure you want to delete board?");
      if(!confirmed){
        return;
      }

      this.boardService.deleteBoard(this.id).subscribe({
      next: () => this.router.navigateByUrl('/')
    })
  }
}
