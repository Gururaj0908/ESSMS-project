import { Injectable } from '@angular/core';
import en_in from '../../i18n/lang.en-in.json';
import hi_in from '../../i18n/lang.hi-in.json';
import { GlobalService } from './global.service';
import { Constants } from '../constants';
@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  public static GetLocale() {
    var lang = localStorage.getItem(Constants.SELECTED_LANGUAGE_CODE);
    if (lang==null) lang="en_US";
    return lang.replace('_', '-');
  }

  constructor(private gs: GlobalService) { }

  public GetString(key: string, ...args: any[]) {
    let part = this.GetLanguageFile()[key];
    if (part == null) {
      part = en_in[key];
    }
    let mes: string = part.message;
    for (let index = 0; index < args.length; index++) {
      const a = args[index];
      mes = mes.replace('{' + index + '}', a);
    }
    return mes;
  }

  private GetLanguageFile() {
    const lang = this.gs.LanguageCode;
    switch (lang) {
      case 'hi_in':
        return hi_in;
      case 'en_in':
      default:
        return en_in;
    }
  }


}
