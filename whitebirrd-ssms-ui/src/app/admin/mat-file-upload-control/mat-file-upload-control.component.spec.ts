import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatFileUploadControlComponent } from './mat-file-upload-control.component';

describe('MatFileUploadControlComponent', () => {
  let component: MatFileUploadControlComponent;
  let fixture: ComponentFixture<MatFileUploadControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatFileUploadControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatFileUploadControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
