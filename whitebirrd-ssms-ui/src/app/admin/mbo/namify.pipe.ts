import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'namify'
})
export class NamifyPipe implements PipeTransform {

  transform(value: string): string {
    if (!value) {
      return value;
    }
    let val = value.replace(/([A-Z]+)/g, ' $1').replace(/([A-Z][a-z])/g, ' $1');
    val = val.replace('_', ' ');
    val = val.charAt(0).toUpperCase() + val.slice(1);
    return val;
  }

}
