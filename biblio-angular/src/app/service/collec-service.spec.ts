import { TestBed } from '@angular/core/testing';

import { CollecService } from './collec-service';

describe('CollecService', () => {
  let service: CollecService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollecService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
