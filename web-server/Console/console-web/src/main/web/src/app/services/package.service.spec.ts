import { TestBed } from '@angular/core/testing';

import { PackageService } from './package.service';

describe('Package.ServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PackageService = TestBed.get(PackageService);
    expect(service).toBeTruthy();
  });
});
