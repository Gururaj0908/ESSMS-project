import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FakeProductIdentificationComponent } from './fake-product-identification.component';

describe('FakeProductIdentificationComponent', () => {
  let component: FakeProductIdentificationComponent;
  let fixture: ComponentFixture<FakeProductIdentificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FakeProductIdentificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FakeProductIdentificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
