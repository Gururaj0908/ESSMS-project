import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorFormTestComponent } from './editor-form-test.component';

describe('EditorFormTestComponent', () => {
  let component: EditorFormTestComponent;
  let fixture: ComponentFixture<EditorFormTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorFormTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorFormTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
