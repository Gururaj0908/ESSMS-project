import { RemoteValidationDirective } from './remote-validation.directive';
import { RemoteService } from '../../../services/remote.service';

describe('RemoteValidationDirective', () => {
  it('should create an instance', () => {
    const directive = new RemoteValidationDirective();
    expect(directive).toBeTruthy();
  });
});
