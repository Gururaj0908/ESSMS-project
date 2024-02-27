import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkedSelectListComponent } from './linked-select-list.component';

describe('LinkedSelectListComponent', () => {
  let component: LinkedSelectListComponent;
  let fixture: ComponentFixture<LinkedSelectListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinkedSelectListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkedSelectListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
