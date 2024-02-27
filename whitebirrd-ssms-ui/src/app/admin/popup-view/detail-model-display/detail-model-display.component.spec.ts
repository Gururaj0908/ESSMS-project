import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailModelDisplayComponent } from './detail-model-display.component';

describe('DetailModelDisplayComponent', () => {
  let component: DetailModelDisplayComponent;
  let fixture: ComponentFixture<DetailModelDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailModelDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailModelDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
