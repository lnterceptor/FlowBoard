import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColumnDetailModalComponent } from './column-detail-modal-component';

describe('ColumnDetailModalComponent', () => {
  let component: ColumnDetailModalComponent;
  let fixture: ComponentFixture<ColumnDetailModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColumnDetailModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColumnDetailModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
