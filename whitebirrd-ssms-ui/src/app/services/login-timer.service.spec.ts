import { TestBed, inject } from '@angular/core/testing';

import { LoginTimerService } from './login-timer.service';

describe('LoginTimerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginTimerService]
    });
  });

  it('should be created', inject([LoginTimerService], (service: LoginTimerService) => {
    expect(service).toBeTruthy();
  }));
});
