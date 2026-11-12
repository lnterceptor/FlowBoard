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
import { ModifyLabels } from '../../components/modify-labels/modify-labels';
import { LabelData } from '../../interfaces/label-data';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { AuthService } from '../../services/auth-service';
import { User } from '../../interfaces/user';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-board',
  imports: [Column, DragDropModule, MatButtonModule, MatIcon],
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
  labels: LabelData[] = [];
  menuOpen = false;
  ownerId: number = 0;
  user : User | null = null;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }
  constructor(public snackBar: MatSnackBar ,private dialog: MatDialog, private columnService:ColumnService, private route: ActivatedRoute, private boardService: BoardService, private authService: AuthService){
    this.authService.currentUser.subscribe(
      user => this.user = user
    )
  }

  async ngOnInit(){
    const idParam = this.route.snapshot.paramMap.get('id');
    this.id = idParam ? + idParam : 0;
    await this.columnService.getColumnsFromBackend(this.id);
    this.boardService.getBoardDataFromBackend(this.id).subscribe({
      next: (board: BoardSummary) =>{
        this.title = board.title;
        this.description = board.description;
        this.collaborators = board.collaborators;
        this.labels = board.labels;
        this.ownerId = board.ownerId
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
        width: '60vw',
        height: '30vh',
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
      height: '55vh',
      disableClose: true,
      data:{boardId: this.id, collaborators: this.collaborators}
    })
    dialogRef.afterClosed().subscribe((collaborators: string[]) =>
    this.collaborators = collaborators)
  }

  modifyLabels(){
    const dialogRef = this.dialog.open(ModifyLabels, {
      disableClose: true,
      width: '60vw',
      maxHeight: '80vh',
      data:{boardId: this.id, labels: this.labels}
    });
    dialogRef.afterClosed().subscribe((labels: LabelData[]) =>{
      this.labels = labels;
    });
  }

  updateBoard(){
    const dialogRef = this.dialog.open(BoardsDetailModalComponent, {
      width: '55vw',
      height: '45vh',
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
  goToProfile(){
    this.router.navigateByUrl('/profile');
  }
  logout(){
    this.authService.logout();
    this.router.navigateByUrl('/sign-in');
  }

  deleteBoard(){
      const confirmed = window.confirm("Are you sure you want to delete board?");
      if(!confirmed){
        return;
      }

      this.boardService.deleteBoard(this.id).subscribe({
      next: () => {this.router.navigateByUrl('/')},
      error:(error: HttpErrorResponse) =>{
        this.snackBar.open(error.error.message, '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
      }
    })
  }

  leaveBoard(){
    const confirmed = window.confirm("Are you sure you want to leave this board?");
      if(!confirmed){
        return;
      }


    this.boardService.deleteCollaborator(this.user?.username!, this.id).subscribe({
      next:() => {          
        this.router.navigateByUrl(`/`);},
      error:() =>{
        this.snackBar.open("Error occured when trying to leave board", '', {duration: 2000,horizontalPosition: 'right', verticalPosition: 'top' , panelClass: ['snackbar']});
      }
      }
  )    
  }
}