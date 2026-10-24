import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyLabels } from './modify-labels';

describe('ModifyLabels', () => {
  let component: ModifyLabels;
  let fixture: ComponentFixture<ModifyLabels>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifyLabels]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyLabels);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
