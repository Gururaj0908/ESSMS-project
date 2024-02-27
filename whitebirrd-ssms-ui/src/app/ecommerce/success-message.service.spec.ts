import { TestBed, inject } from '@angular/core/testing';

import { SuccessMessageService } from './success-message.service';

describe('SuccessMessageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SuccessMessageService]
    });
  });

  it('should be created', inject([SuccessMessageService], (service: SuccessMessageService) => {
    expect(service).toBeTruthy();
  }));
});
