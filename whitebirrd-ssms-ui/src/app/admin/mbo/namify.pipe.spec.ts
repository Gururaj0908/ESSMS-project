import { NamifyPipe } from './namify.pipe';

describe('NamifyPipe', () => {
  it('create an instance', () => {
    const pipe = new NamifyPipe();
    expect(pipe).toBeTruthy();
  });
});
