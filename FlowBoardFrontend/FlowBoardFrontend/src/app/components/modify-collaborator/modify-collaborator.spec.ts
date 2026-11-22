import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyCollaborator } from './modify-collaborator';

describe('ModifyCollaborator', () => {
  let component: ModifyCollaborator;
  let fixture: ComponentFixture<ModifyCollaborator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifyCollaborator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyCollaborator);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
