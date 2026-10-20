import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardsDetailModalComponent } from './boards-detail-modal-component';

describe('BoardsDetailModalComponent', () => {
  let component: BoardsDetailModalComponent;
  let fixture: ComponentFixture<BoardsDetailModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoardsDetailModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardsDetailModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
