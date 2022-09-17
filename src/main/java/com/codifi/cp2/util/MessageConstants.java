package com.codifi.cp2.util;

public class MessageConstants {
    public static final String SCHEME_NAME = "sap_cp2.";
    public static final int FAILED_STATUS = 0;
    public static final int SUCCESS_STATUS = 1;

    public static final String FAILED_MSG = "Failed";
    public static final String SUCCESS_MSG = "Success";
    // JWT
    // public static final String CONST_BEARER = "Bearer ";
    // public static final String CONST_SESSIONS = "/sessions";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String LOGIN_URL = "/user/login";
    public static final String PACKAGE = "com.codifi.cp2";
    // Expiry in milleseconds
    public static final int EXPIRY_IN_MILLESECONDS = 900000;
    public static final String STRING_PATTERN = "[^a-zA-Z0-9 ]";
    public static final String DIGIT_PATTERN = "[0-9]+";
    // URL Methods
    // public static final String GET_METHOD = "GET";
    // public static final String POST_METHOD = "POST";
    // public static final String PUT_METHOD = "PUT";
    // public static final String DELETE_METHOD = "DELETE";
    public static final String AUTHORIZATION = "Authorization";
    // public static final String CONTENT_TYPE = "Content-Type";
    public static final String USER_AGENT = "user-agent";
    // public static final String APPLICATION_JSON = "application/json";
    // public static final String TEXT_PLAIN = "text/plain";
    // public static final String APPLICATION_CONST = "Application";
    // public static final String VERSION_CONST = "Version";
    // public static final String PASSWORD = "password";
    // public static final String TRUE = "true";
    // public static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";
    // public static final String NONE = "NONE";
    // Messages
    public static final String NOTE_TYPE_RGW = "RGW";
    public static final String NOTE_TYPE_FTW = "FTW";
    public static final String CONTACT_ADMIN = "Error while parsing data. Please contact admin";
    public static final String EMPTY_DATA = "Params is empty";
    public static final String USER_NAME_NULL = "User Name Cannot Be empty";
    public static final String PASSWORD_NULL = "Password Cannot Be empty";
    public static final String WRONG_USER = "No User Found from this Credentials";
    public static final String PAGEID_NULL = "Page Id cannot be null";
    public static final String LANGID_NULL = "Language Id cannot be null";
    public static final String DATA_NULL = "No data found";
    public static final String PAGEID_WRONG = "The given page Id is wrong. Please give correct page Id";
    public static final String PAGE_NOT_ACTIVATED = "The selected page is not activated";
    public static final String LANGID_WRONG = "The given Language Id is wrong. Please give correct Language Id";
    public static final String LANGUAGE_NOT_ACTIVATED = "The selected Language is not activated";
    public static final String TEXT_ID_NULL = "The given Text Id is null.";
    public static final String INVALID_TEXT_ID = "The given Text Id is invalid.";
    public static final String SPECIAL_CHAR_TEXT_NAME = "Special Character Found in Text Name";
    public static final String TEXT_NAME_NULL = "The given Text Name is null.";
    public static final String TEXT_DESCRIPTION_NULL = "The given Text Description is null.";
    public static final String SPECIAL_CHAR_TEXT_DESCRIPTION = "Special Character Found in Text Description";
    public static final String LANGUAGE_NULL = "Language is Null";
    public static final String SHORT_FORM_NULL = "ShortForm is Null";
    public static final String SPECIAL_CHAR_LANGUAGE = "Special Character Found in Language";
    public static final String SPECIAL_CHAR_SHORT_FORM = "Special Character Found in ShortForm";
    public static final String PAGE_NAME_NULL = "page Name is null";
    public static final String PAGE_DESC_NULL = "page Description is null";
    public static final String SPECIAL_CHAR_PAGE_NAME = "Special Character Found in Page Name";
    public static final String SPECIAL_CHAR_PAGE_DESC = "Special Character Found in Page Description";
    public static final String ID_NULL = "Id Cannot be null";
    public static final String CREATED_BY_NULL = "Created by Cannot be null";
    public static final String STATUS_NULL = "Status Cannot be null";
    public static final String DELETE_SUCCESS = "Record deleted successfully";
    public static final String RECORD_NOT_FOUND = "Record not found on this ID - ";
    public static final String ERROR_UPDATE_RECORD = "Error While Updating the Record";
    public static final String ERROR_INSERT_ATTACHMENT = "Insert Attachment failed";
    public static final String ATTACHMENT_NOT_FOUND = "Attachemnt not found";
    public static final String ACTIVE_STATUS_NOT_DELETE = "Active Status Record Cannot be delete.";
    public static final String FREE_TEXT_TRIGGER_NAME_EXIST = "Free Text Trigger name already exists.";
    public static final String AUTOMATED_TRIGGER_NAME_EXIST = "Automated Trigger name already exists.";
    public static final String CATEGORY_TECHNOLOGY_NAME_EXIST = "Category technical name already exists.";
    public static final String RECEIVER_TRIGGER_NAME_EXIST = "Receiver name already exists.";
    public static final String RECEIVER_TRIGGER_NAME_NULL = "Receiver name is null.";
    public static final String RECEIVER_TRIGGER_ID_NULL = "Receiver Id is null.";
    public static final String ERROR_WHILE_SAVING_DATA = "Error While saving values.!";
    public static final String ERROR_WHILE_UPDATING_DATA = "Error While Updating values.!";
    public static final String MISSING_MANDATORY_FIELDS = "Please Fill Mandatory Fields";
    public static final String RECORD_NOT_ACTIVE = "This Record is not Active";
    // Extracted Data Field list Constants
    public static final String TABLE_NAME_NULL = "Table Name is null";
    public static final String FIELD_NAME_NULL = "Field Name is null";
    public static final String ODATA_SERVICE_NULL = "ODATA Service is null";
    public static final String MESSAGE_NAME_NULL = "Message Name is null";
    public static final String SAP_FIELD_NAME_NULL = "SAP Field Name is null";
    public static final String EASY_NAME_NULL = "Easy Name is null";
    public static final String DATE_TYPE_NULL = "Date Type is null";
    public static final String MESSAGE_RELEVANT_NULL = "Message Relevant is null";
    public static final String SPECIAL_CHAR_IN_TABLE_NAME = "Special Chanracter found in Table Name";
    public static final String SPECIAL_CHAR_IN_FIELD_NAME = "Special Chanracter found in Field Name";
    public static final String SPECIAL_CHAR_IN_ODATA_SERVICE = "Special Chanracter found in ODATA Service";
    public static final String SPECIAL_CHAR_IN_MESSAGE_NAME = "Special Chanracter found in Message Name";
    public static final String SPECIAL_CHAR_IN_SAP_FIELD_NAME = "Special Chanracter found in SAP Field Name";
    public static final String SPECIAL_CHAR_IN_EASY_NAME = "Special Chanracter found in Easy Name";
    public static final String SPECIAL_CHAR_IN_DATE_TYPE = "Special Chanracter found in Date Type";
    // RGW
    public static final String MESSAGE_ID_NOT_EXSITS = "Message Id does not exsits.";
    public static final String MESSAGE_ID_NULL = "Message Id is null";
    public static final String CONST_STATUS_UD = "Under Development";
    public static final String CONST_STATUS_RFA = "Ready for Approval";
    public static final String CONST_STATUS_ACTIVE = "Active";
    public static final String CONST_STATUS_IN_ACTIVE = "Inactive";
    public static final String CONST_STATUS_ASSIGN = "assign";
    public static final String CONST_STATUS_CLAIM = "claim";
    public static final String CONST_STATUS_RESET = "reset";
    public static final String CONST_STATUS_START = "start";
    public static final String CONST_STATUS_COMPLETED = "Completed";
    public static final String CONST_STATUS_POSTPONED = "Postponed";
    public static final String CONST_STATUS_CREATED = "Created";
    public static final String CONST_STATUS_AWAITING_APPROVAL = "Awaiting Approval";
    public static final String CONST_STATUS_APPROVED = "Approved";
    public static final String CONST_STATUS_REJECTED = "Rejected";
    public static final String CONST_IN_PROGRESS = "In Progress";
    public static final String TRIGGER_EVENT_NULL = "The Trigger Event cannot be null";
    public static final String INVALID_TRIGGER_EVENT = "Invalid Trigger Event";
    //FTW
    public static final String SCREEN_DETAIL = "Detail";
    public static final String SCREEN_TRIGGER = "Trigger";
    public static final String SCREEN_DISTRIBUTIONLIST = "DistributionList";
    // User Information
    public static final String USER_ID_EXIST = "User ID is already Exists";
    public static final String USER_ID_NULL = "User ID is null";
    // Cp2 Constants
    public static final String SAVE_OBJECT = "Record Saved Successfully.";
    public static final String EDIT_OBJECT = "Record Updated Successfully.";
    public static final String DELETE_OBJECT = "Record Deleted Successfully.";
    public static final String DATA_NOT_FOUND = "Data Not Found.";
    public static final String ERROR_OCCURED = "Something Went Wrong,Please Try Again Later.";
    public static final String SUCCESS = "successfully.";
    public static final String FAILED = "failed.";
    public static final String SUCCESS_CODE = "200";
    public static final String FAILED_CODE = "404";
    public static final String ERROR_CODE = "500";
    public static final String URL = "https://helloscp.cfapps.eu20.hana.ondemand.com";
    public static final String STATUS_UPDATED = "Status Updated Successfully.";
    public static final String STATUS_NOT_UPDATED = "Status Not Updated";

}
