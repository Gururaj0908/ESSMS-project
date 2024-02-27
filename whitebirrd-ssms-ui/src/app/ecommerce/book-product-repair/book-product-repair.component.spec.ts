import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookProductRepairComponent } from './book-product-repair.component';

describe('BookProductRepairComponent', () => {
  let component: BookProductRepairComponent;
  let fixture: ComponentFixture<BookProductRepairComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookProductRepairComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookProductRepairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
