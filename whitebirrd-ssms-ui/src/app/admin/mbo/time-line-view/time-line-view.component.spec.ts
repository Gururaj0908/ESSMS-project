import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeLineViewComponent } from './time-line-view.component';

describe('TimeLineViewComponent', () => {
  let component: TimeLineViewComponent;
  let fixture: ComponentFixture<TimeLineViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimeLineViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeLineViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
