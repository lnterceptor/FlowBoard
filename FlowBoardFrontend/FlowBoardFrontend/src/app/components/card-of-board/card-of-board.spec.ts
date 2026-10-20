import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardOfBoard } from './card-of-board';

describe('CardOfBoard', () => {
  let component: CardOfBoard;
  let fixture: ComponentFixture<CardOfBoard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardOfBoard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardOfBoard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
