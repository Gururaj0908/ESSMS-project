import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicalProcessComponent } from './technical-process.component';

describe('TechnicalProcessComponent', () => {
  let component: TechnicalProcessComponent;
  let fixture: ComponentFixture<TechnicalProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TechnicalProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnicalProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
