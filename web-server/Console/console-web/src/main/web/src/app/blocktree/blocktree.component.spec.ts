import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlocktreeComponent } from './blocktree.component';

describe('BlocktreeComponent', () => {
  let component: BlocktreeComponent;
  let fixture: ComponentFixture<blocktreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlocktreeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlocktreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
