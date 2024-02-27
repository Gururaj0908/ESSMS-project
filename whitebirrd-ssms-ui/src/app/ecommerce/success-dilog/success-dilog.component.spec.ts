import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessDilogComponent } from './success-dilog.component';

describe('SuccessDilogComponent', () => {
  let component: SuccessDilogComponent;
  let fixture: ComponentFixture<SuccessDilogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SuccessDilogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessDilogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
