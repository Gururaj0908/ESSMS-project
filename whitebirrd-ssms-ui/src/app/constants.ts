export class Constants {
    public static SELECTED_MENU = 'selectedMenu';
    public static LOGGED_USER = 'loggedUser';
    public static MENU_ITEMS = 'menuItems';
    public static CREATE_URL = 'createUrl';
    public static UPDATE_URL = 'updateUrl';
    public static ENTITY_FORM_DATA = 'entityFormData';
    public static FORM_EDITOR = 'formEditor';
    public static APPLICATION_USERS = 'appUser';
    public static NON_URL_TABLE_DATA = 'nonUrlTableData';
    public static SELECTED_LANGUAGE_CODE = 'selectLanguageCode';
}

export class ValidationError {
  field: string;
  errorMsg: string;
}