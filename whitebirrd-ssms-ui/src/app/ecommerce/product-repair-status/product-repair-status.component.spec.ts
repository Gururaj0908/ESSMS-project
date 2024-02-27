import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductRepairStatusComponent } from './product-repair-status.component';

describe('ProductRepairStatusComponent', () => {
  let component: ProductRepairStatusComponent;
  let fixture: ComponentFixture<ProductRepairStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductRepairStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductRepairStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
