import { TestBed, inject } from '@angular/core/testing';

import { MenuHelperService } from './menu.helper.service';

describe('MenuHelperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MenuHelperService]
    });
  });

  it('should be created', inject([MenuHelperService], (service: MenuHelperService) => {
    expect(service).toBeTruthy();
  }));
});
