import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectorPopupComponent } from './selector-popup.component';

describe('SelectorPopupComponent', () => {
  let component: SelectorPopupComponent;
  let fixture: ComponentFixture<SelectorPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectorPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectorPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
