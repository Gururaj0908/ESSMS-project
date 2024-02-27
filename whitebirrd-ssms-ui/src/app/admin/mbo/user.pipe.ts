import { Pipe, PipeTransform } from '@angular/core';
import { Constants } from '../../constants';

@Pipe({
  name: 'user',
  // pure: false
})
export class UserPipe implements PipeTransform {

  transform(value: string): string {
    if (UsersHost.Users == null) {
      UsersHost.Users = JSON.parse(sessionStorage.getItem(Constants.APPLICATION_USERS));
    }
    if (UsersHost.Users) {
      const user = UsersHost.Users.find(t => t.value.toLowerCase() === value.toLowerCase());
      if (user) {
        return user.label;
      }
    }
    return value;
  }
}

export class UsersHost {
  public static Users;
}
