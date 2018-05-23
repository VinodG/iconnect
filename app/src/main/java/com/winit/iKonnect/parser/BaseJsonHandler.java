package com.winit.iKonnect.parser;

import com.winit.common.webAccessLayer.ServiceUrls;

public abstract class BaseJsonHandler {

    protected int status;
    protected String message = "Some problem occured. Please contact Admin.";
    public boolean isError(){
        return status==0?true:false;
    }
    public abstract Object getData();
    public abstract void parse(StringBuilder response);

    public static BaseJsonHandler getParser(ServiceUrls.ServiceAction serviceAction) {

        switch (serviceAction) {
            case FEEDS:
                return new FeedsParser();
            case SINGLE_FEEDS:
                return new FeedsParser();
            case POST_FEED_ACTION:
                return new PostFeedActionParser();
            case GET_COMMENTS:
                return new CommentsParser();
            case GET_LIKES:
                return new LikesParser();
            case GET_CATEGORIES:
                return new CategoriesParser();
            case NEW_SERVICE_REQUEST:
                return new FormResponseParser();
            case GET_TRACKING_DETAIL:
                return new TrackServiceResponseParser();
            case GET_PAYSLIP_DETAIL:
                return new PaySlipResponseParser();
            case POST_LOGIN_DATA:
                return new LogInResponseParser();
            case POST_OTP_REQUEST:
                return new LogInResponseParser();
            case GET_FORGET_PASSWORD:
                return new ForgetPasswordParser();
            case Load_CALENDER_DATA:
                return new CalenderDetailParser();
            case GET_THOUGHT_OF_THE_DAY:
                return new ThoughtOfTheParser();
            case POST_SIGN_UP:
                return new LogInResponseParser();
            case POST_OTP_VALIDATION:
                return new LogInResponseParser();
            case POST_STAFF_REGISTER:
                return new LogInResponseParser();
            case NOTIFICATION_DETAILS:
                return new NotificationDetailParser();
            case GET_INBOX:
                return new InboxParser();
            case GET_CHAT_GROUP:
                return new ChatGroupParser();
            case POST_CONTACT_US:
                return new FormResponseParser();
            case GET_CHANGE_PASSWORD:
                return new BaseResponseParser();
            case UPDATE_PROFILE_PIC:
                return new BaseResponseParser();
            case GET_WELCOME_MESSAGE:
                return new WelcomeMessageParser();
            case GET_FORM_STATUS:
                return new FormStatusParser();
            case GET_EMPLOYEE_NAMES:
                return new EmployeeFilterParsor();
            case POST_HOVERING_SERVICE:
                return new GetHoveringParser();
            case GET_SERVICE_HISTORY:
                return new GetServiceHistoryParser();
            case GET_USER_DOCUMENTS:
                return new GetUserDocumentsParser();

            case GET_CONTACT_US_LIST:
                return new GetContactListParser();
            case GET_EMP_DETAILS:
                return new GetEmployeeListParser();
        }
        return null;
    }

}
