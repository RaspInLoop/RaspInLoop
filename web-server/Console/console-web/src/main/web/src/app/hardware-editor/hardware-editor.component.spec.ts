import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HardwareEditorComponent } from './hardware-editor.component';

describe('HardwareEditorComponent', () => {
  let component: HardwareEditorComponent;
  let fixture: ComponentFixture<HardwareEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HardwareEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HardwareEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
