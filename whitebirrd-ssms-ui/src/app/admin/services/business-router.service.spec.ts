import { TestBed, inject } from '@angular/core/testing';

import { BusinessRouterService } from './business-router.service';

describe('BusinessRouterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BusinessRouterService]
    });
  });

  it('should be created', inject([BusinessRouterService], (service: BusinessRouterService) => {
    expect(service).toBeTruthy();
  }));
});
