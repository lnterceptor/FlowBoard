import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewBoardCard } from './add-new-board-card';

describe('AddNewBoardCard', () => {
  let component: AddNewBoardCard;
  let fixture: ComponentFixture<AddNewBoardCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewBoardCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewBoardCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
