export class ErrorValidatorService {
  static getValidatorErrorMessage(validatorName: string, validatorValue?: any) {
    const config = {
      'required': 'Please enter a value',
      'requiredEmail': 'Email ID is mandatory.',
      'requiredFirstName': ' Firstname is mandatory.',
      'requiredUsernName': 'Username is madatory.',
      'invalidPhoneNo': 'Please enter a valid phone no.',
      'invalidZipCode': 'Please enter a valid pin code.',
      'validateFirstName': ' Firstname cannot contain alphanumeric characters.',
      'invalidEmailAddress': 'Incorrect email ID.',
      'invalidPassword': 'Invalid password. Password must be at least 6 characters long, and contain a number.',
      'alphabets': 'Enter alphabets only',
      'username': 'Only letters, numbers ,(_) allowed',
      'minlength': `Minimum length ${validatorValue.requiredLength}`,
      'compare': 'Value does not match',
      'email': 'Email is not valid'
    };

    return config[validatorName];
  }

  static emailValidator(control) {

    if (control.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
      return null;
    } else {
      return { 'invalidEmailAddress': true };
    }
  }
  // var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  static phoneNoValidator(control) {
    console.log(control);
    if (control.value.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)) {
      return null;
    } else {
      return { 'invalidPhoneNo': true };
    }
  }
  static zipCodeValidator(control) {

    if (control.value.match(/^[1-9][0-9]{5}$/)) {
      return null;
    } else {
      return { 'invalidZipCode': true };
    }
  }

  static passwordValidator(control) {
    if (control.value.match(/^[A-Za-z0-9]+(?:[ _][A-Za-z0-9]+)*$/)) {
      return null;
    } else {
      return { 'invalidPassword': true };
    }
  }
  static userNameValidator(control) {
    if (control.value.match(/^[A-Za-z0-9]+(?:[._][A-Za-z0-9]+)*$/)) {
      return null;
    } else {
      return { 'username': true };
    }
  }
  static nameValidator(control) {
    if (control.value.match(/^[A-Za-z]*$/)) {
      return null;
    } else {
      return { 'alphabets': true };
    }
  }
  static firstNameValidator(control) {
    if (control.value.match(/^[A-Za-z]*$/)) {
      return null;
    } else {
      return { 'alphabets': true };
    }
  }
  static requiredFirstNameValidator(control) {
    if (control.value !== '') {
      return null;
    } else {
      return { 'requiredFirstName': true };
    }
  }
  static requiredValidator(control) {
    if (control.value !== '') {
      return null;
    } else {
      return { 'required': true };
    }
  }

  static requiredUsernNameValidator(control) {
    if (control.value !== '') {
      return null;
    } else {
      return { 'requiredUsernName': true };
    }
  }
  static requiredEmailValidator(control) {
    if (control.value !== '') {
      return null;
    } else {
      return { 'requiredEmail': true };
    }
  }


}
