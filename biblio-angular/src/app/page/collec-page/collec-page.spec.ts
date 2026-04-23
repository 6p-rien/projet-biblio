import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollecPage } from './collec-page';

describe('CollecPage', () => {
  let component: CollecPage;
  let fixture: ComponentFixture<CollecPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollecPage],
    }).compileComponents();

    fixture = TestBed.createComponent(CollecPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
