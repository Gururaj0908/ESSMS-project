import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StageSelectionComponent } from './stage-selection.component';

describe('StageSelectionComponent', () => {
  let component: StageSelectionComponent;
  let fixture: ComponentFixture<StageSelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StageSelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StageSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
