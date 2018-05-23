package com.winit.iKonnect.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.winit.common.Preference;
import com.winit.common.application.IKonnectApplication;
import com.winit.common.constant.AppConstants;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.common.gcm.PushNotificationListener;
import com.winit.common.util.CalendarUtil;
import com.winit.common.util.ChatCountUtils;
import com.winit.common.util.Flip3dAnimation;
import com.winit.common.util.NetworkUtility;
import com.winit.common.util.StringUtils;
import com.winit.common.util.UnCaughtException;
import com.winit.common.webAccessLayer.ServiceUrls;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.AttachmentsDO;
import com.winit.iKonnect.dataobject.MessageDO;
import com.winit.iKonnect.dataobject.ServiceDO;
import com.winit.iKonnect.ui.customview.SignatureView;
import com.winit.iKonnect.ui.dialog.CustomDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.winit.common.constant.AppConstants.getSignatureImageFile;
import static com.winit.common.util.CalendarUtil.DD_MMM_YYYY_PATTERN;
import static com.winit.common.util.CalendarUtil.FULL_DATE_FORMATE;
import static com.winit.common.util.CalendarUtil.findTwoDaysCount;
import static com.winit.iKonnect.dataobject.ServiceDO.ServiceType.BANK_ACCOUNT_UPDATE;
import static com.winit.iKonnect.dataobject.ServiceDO.ServiceType.HR_SERVICE_REQUEST;
import static com.winit.iKonnect.dataobject.ServiceDO.ServiceType.SALARY_REQUEST;
import static com.winit.iKonnect.dataobject.ServiceDO.ServiceType.VISIT_VISA;


/**
 * Created by Girish Velivela on 3/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements PushNotificationListener, ChatCountUtils.MessageCountListner {

    @Bind(R.id.flBody)
    FrameLayout flBody;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.appBarLayout)
    LinearLayout appBarLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.rlFormBar)
    RelativeLayout rlFormBar;
    @Bind(R.id.llFormBar)
    LinearLayout llFormBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.tvFormName)
    TextView tvFormHeading;
    @Bind(R.id.ivHistory)
    ImageView ivHistory;
    @Bind(R.id.ivFormIcon)
    ImageView ivFormIcon;

    @Bind(R.id.view)
    Button view;

    public CircleImageView ivProfile;
    // -------------------------- Tool Bar ------------------------
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private TextView tvTitle;
    private String selectedLanguage;
    // -------------------------- Tool Bar End------------------------

    //---------------------------Track service header---------------------
    @Bind(R.id.llTrackHeader)
    LinearLayout llTrackHeader;
    @Bind(R.id.ivFormIcon_track)
    ImageView ivFormIcon_track;
    @Bind(R.id.tvFormName_track)
    TextView tvFormName_track;
    @Bind(R.id.tvReq_Date)
    TextView tvReq_Date;
    //---------------------------Track service header---------------------
    //--------------------------- Profile Header----------------------------
    @Bind(R.id.llEmployeeHeader)
    LinearLayout llEmployeeHeader;
    @Bind(R.id.tvUserName_Profile)
    TextView tvUser_Name;
    @Bind(R.id.tvUserDesignation_Profile)
    TextView tvUserDesignation;
    @Bind(R.id.tvUserId_Profile)
    TextView tvUser_Id;
    @Bind(R.id.tvLocation)
    TextView tvLocation;
    @Bind(R.id.tvFunctionalArea)
    TextView tvFunctionalArea;
    @Bind(R.id.tvNationality)
    TextView tvNationality;
    @Bind(R.id.ivEmployeeUserPic_Profile)
    CircleImageView ivEmployeeUserPic;
    //--------------------------- Profile Header----------------------------

    protected LayoutInflater inflater;
    public Preference preference;
    private ProgressDialog progressdialog;
    protected boolean isLoggedIn, isBackAllowed;
    private Toast toast;
    protected String className = "";
    public String capturedImagePath = "";
    public static float px;
    public FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference chatPersonalRef, groupChatRef;
    public ChatCountUtils chatCountUtils;

    private Context mContext;
    public static int height;
    public static int width;

    @Override
    public void data(String msgbdy, String title) {

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!(BaseActivity.this instanceof TrackServiceActivity) || !(BaseActivity.this instanceof DashboardActivity))
                finish();
        }
    };

    private void applyFontToMenuItem(MenuItem mi) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", AppConstants.REGULAR), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNewTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.quaote_color)), 0, mNewTitle.length(), 0);
        mi.setTitle(mNewTitle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(BaseActivity.this));
        setContentView(R.layout.base_activity);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mContext = BaseActivity.this;

        //*************************** Tool Bar intialisation **************************/
        setSupportActionBar(toolbar);
        //*************************** Tool Bar intialisation Ends **************************/

        //*************************** Common intialisation **************************/
        preference = Preference.getInstance();
        inflater = this.getLayoutInflater();
        //*************************** Common intialisation **************************/

        if (px == 0.0f) {
            Resources r = getResources();
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        ////************************************Firebase Refrences Initialisation********************************************************
        chatPersonalRef = FirebaseDatabase.getInstance().getReference().child("ChatPersonal");
        groupChatRef = FirebaseDatabase.getInstance().getReference().child("ChatGroups");
        chatCountUtils = new ChatCountUtils(BaseActivity.this, chatPersonalRef, groupChatRef);
        ////**************************************************************************************************************

        setFabVisiblity();
        lockDrawer();
        toolbarLogo();
        setStatusBarColor();
        setToolbarTitle("");
        manageToolbar();
        initialize();

        if (savedInstanceState != null) {
            AppConstants.DATABASE_PATH = getApplication().getFilesDir().toString() + "/";
            AppConstants.initializeTypeFace();
        }
        setTypeFace(flBody);

        selectedLanguage = preference.getStringFromPreference(Preference.LANGUAGE, "");
        if (selectedLanguage.equalsIgnoreCase(AppConstants.ARABIC)) {
            flBody.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            flBody.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    @Override
    public void UpdateMessageCount(HashMap<String, Vector<MessageDO>> hmPersonalChatCount, HashMap<String, Vector<MessageDO>> hmGroupUnreadMessages) {
        int count = 0;
        if (hmPersonalChatCount != null && hmPersonalChatCount.size() > 0) {
            Set<String> keys = hmPersonalChatCount.keySet();
            for (String key : keys)
                count += hmPersonalChatCount.get(key).size();
        }
        if (hmGroupUnreadMessages != null && hmGroupUnreadMessages.size() > 0) {
            Set<String> keys = hmGroupUnreadMessages.keySet();
            for (String key : keys)
                count += hmGroupUnreadMessages.get(key).size();
        }

        MenuItem menuItem = navigationView.getMenu().getItem(3);
        TextView view = (TextView) menuItem.getActionView();
        if (view != null && count > 0)
            view.setText("" + count);
        else if (view != null)
            view.setText("");
    }

    @Override
    public void refreshSoloChatMembers(DataSnapshot dataSnapshot) {
        // dont delete this method
    }

    @Override
    public void refreshGroupChatKeys(HashMap<Integer, String> hmChatGroupKeys) {
        // dont delete this method
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLanguage();
        selectedLanguage = preference.getStringFromPreference(Preference.LANGUAGE, "");

        if (flBody != null) {
            if (selectedLanguage.equalsIgnoreCase(AppConstants.ARABIC)) {
                flBody.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            } else {
                flBody.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }
        if (selectedLanguage.equalsIgnoreCase(AppConstants.ARABIC)) {
            navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            drawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            drawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    public void setLanguage() {
        Locale locale = null;
        try {
            selectedLanguage = preference.getStringFromPreference(Preference.LANGUAGE, "");
            if (selectedLanguage != null && !selectedLanguage.equalsIgnoreCase("")) {
                if (selectedLanguage.equalsIgnoreCase(AppConstants.ARABIC)) {
                    locale = new Locale("ar");
                } else {
                    locale = new Locale("en_US");
                }
                DisplayMetrics dm = getResources().getDisplayMetrics();
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getApplicationContext().getResources().updateConfiguration(config, dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //listener for home
        if (item.getItemId() == android.R.id.home) {
            if (isBackAllowed)
                finish();
            else
                drawerLayout.openDrawer(navigationView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void setAttachment(String path, AttachmentsDO attachmentsDO) {
        Toast.makeText(BaseActivity.this, "Implement the funtionality", Toast.LENGTH_LONG).show();
    }

    protected abstract void initialize();

    //******************************************************************** App Bar Start **************************************************/

    private void setStatusBarColor() {
        Window window = BaseActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(BaseActivity.this.getResources().getColor(R.color.colorPrimaryDark));

            tvFormHeading.setTextColor(getResources().getColor(R.color.app_color));
            tvFormName_track.setTextColor(getResources().getColor(R.color.app_color));
        }
    }

    private void toolbarLogo() {
        if (BaseActivity.this instanceof DashboardActivity) {
            getSupportActionBar().setLogo(R.drawable.app_bar_logo);
            setToolbarTitle("");
        }
    }

    private void manageToolbar() {
        if (BaseActivity.this instanceof DashboardActivity || BaseActivity.this instanceof ChatActivity
                || BaseActivity.this instanceof TrackServiceActivity
                || BaseActivity.this instanceof FormActivity) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                appBarLayout.setOutlineProvider(null);
            }
        }

        if (BaseActivity.this instanceof FormActivity) {
//            view.setVisibility(View.VISIBLE);
            toolbar.setBackground(null);
            appBarLayout.setBackground(getResources().getDrawable(R.drawable.request_form));
            ivFormIcon.setVisibility(View.VISIBLE);
            llFormBar.setVisibility(View.VISIBLE);

            llTrackHeader.setVisibility(View.GONE);
            llEmployeeHeader.setVisibility(View.GONE);
            ivEmployeeUserPic.setVisibility(View.GONE);

        } else if (BaseActivity.this instanceof TrackServiceDetails) {
//            view.setVisibility(View.VISIBLE);
            toolbar.setBackground(null);
            appBarLayout.setBackground(getResources().getDrawable(R.drawable.tracking_header_new));
            ivFormIcon.setVisibility(View.GONE);
            llFormBar.setVisibility(View.GONE);

            llTrackHeader.setVisibility(View.VISIBLE);
            llEmployeeHeader.setVisibility(View.GONE);
            ivEmployeeUserPic.setVisibility(View.GONE);
        } else if (BaseActivity.this instanceof StaffDetailActivityNew) {
//            view.setVisibility(View.VISIBLE);
            toolbar.setBackground(null);
            appBarLayout.setBackground(getResources().getDrawable(R.drawable.profile_header));
            ivFormIcon.setVisibility(View.GONE);
            llFormBar.setVisibility(View.GONE);

            llTrackHeader.setVisibility(View.GONE);
            llEmployeeHeader.setVisibility(View.VISIBLE);
            ivEmployeeUserPic.setVisibility(View.VISIBLE);
        } else {

//            view.setVisibility(View.GONE);
            appBarLayout.setBackground(null);
            toolbar.setBackground(getResources().getDrawable(R.drawable.global_app_bar));

            ivFormIcon.setVisibility(View.GONE);
            llFormBar.setVisibility(View.GONE);

            llTrackHeader.setVisibility(View.GONE);
            llEmployeeHeader.setVisibility(View.GONE);
            ivEmployeeUserPic.setVisibility(View.GONE);
        }

    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.app_color));
        if (!StringUtils.isEmpty(title)) {
            Bundle bundle = new Bundle();
            bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        }
    }

    private void lockDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        if (BaseActivity.this instanceof DashboardActivity
                || BaseActivity.this instanceof SettingActivity
                || BaseActivity.this instanceof MyProfileActivity
                || BaseActivity.this instanceof ChatActivity
                || BaseActivity.this instanceof ContactUsActivity
                || BaseActivity.this instanceof InboxActivity

                || BaseActivity.this instanceof CalenderViewDetailActivity
                || BaseActivity.this instanceof TermsandCondition) {
            isBackAllowed = false;
            setUpNavigationView();
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
        } else {
            if (BaseActivity.this instanceof SplashActivity
                    || BaseActivity.this instanceof LoginActivity
                    || BaseActivity.this instanceof SignUpActivity
                    || BaseActivity.this instanceof WelcomeScreen
                    || BaseActivity.this instanceof PasswordActivity
                    || BaseActivity.this instanceof UserNavigationActivity

//                    || BaseActivity.this instanceof NotificationDetailActivity
                    || BaseActivity.this instanceof AppDetailPagerActivity)
                toolbar.setVisibility(View.GONE);
            isBackAllowed = true;
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    public void setUpNavigationView() {
        // ******************************************************* ItemStyling *********************************
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            if (mi.getItemId() == R.id.Inbox) {
                TextView view = (TextView) mi.getActionView();
                if (view != null && preference.getIntFromPreference(Preference.NotificationCount, 0) > 0)
                    view.setText("" + preference.getIntFromPreference(Preference.NotificationCount, 0));
                else if (view != null)
                    view.setText("");
            } else if (mi.getItemId() == R.id.my_Dashboard) {
                TextView view = (TextView) mi.getActionView();
                if (view != null && preference.getIntFromPreference(Preference.PostCount, 0) > 0)
                    view.setText("" + preference.getIntFromPreference(Preference.PostCount, 0));
                else if (view != null)
                    view.setText("");
            }
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
        // ******************************************************* ItemStyling *********************************


        // ******************************************************* Nav Header *********************************
        View header = navigationView.inflateHeaderView(R.layout.nav_header);
        final TextView tvUserName = (TextView) header.findViewById(R.id.tvusername);
        TextView tvQuoteTitle = (TextView) header.findViewById(R.id.tvQuoteTitle);
        ivProfile = (CircleImageView) header.findViewById(R.id.ivProfile);
        final TextView tvDesignation = (TextView) header.findViewById(R.id.tvDesignation);
        final TextView tvuseerid = (TextView) header.findViewById(R.id.tvuseerid);
        TextView tv_thought_of_the_day = (TextView) header.findViewById(R.id.tv_thoughtoftheday);
        LinearLayout ivShare = (LinearLayout) header.findViewById(R.id.img_share);
        IKonnectApplication.setTypeFace((ViewGroup) header, AppConstants.REGULAR);
        IKonnectApplication.setTypeFace(tvUserName, AppConstants.BOLD);
        IKonnectApplication.setTypeFace(tvQuoteTitle, AppConstants.BOLD);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvUserName.setText("" + preference.getStringFromPreference(preference.STAFF_NAME, "N/A"));
                tvDesignation.setText("" + preference.getStringFromPreference(preference.STAFF_POSITION, "N/A"));
                tvuseerid.setText("[" + preference.getStringFromPreference(preference.STAFF_NUMBER, "N/A") + "]");
            }
        },1000);

        if (preference.getStringFromPreference(Preference.LANGUAGE, "").equalsIgnoreCase(AppConstants.ENGLISH))
            tv_thought_of_the_day.setText(preference.getStringFromPreference(Preference.THOUGHT_OF_THE_DAY, ""));
        else
            tv_thought_of_the_day.setText(preference.getStringFromPreference(Preference.THOUGHT_OF_THE_DAY_ARABIC, ""));

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent();
                shareIntent.setType("text/plain");
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "" + preference.getStringFromPreference(Preference.THOUGHT_OF_THE_DAY, ""));
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Select app to share"));

            }
        });

        if (!(BaseActivity.this instanceof DashboardActivity)) {
            String profilPic = preference.getStringFromPreference(preference.STAFF_PHOTO_URL, "");
            if (!StringUtils.isEmpty(profilPic))
                IKonnectApplication.setImageUrl(ivProfile, ServiceUrls.PROFILE_PIC + profilPic, R.drawable.profile_pic);
        }
        ivProfile.setTag(R.string.isRound, true);
        // ******************************************************* Nav Header *********************************

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent = null;
                TextView view = (TextView) menuItem.getActionView();
                if (view != null)
                    view.setText("");
                switch (menuItem.getItemId()) {
                    case R.id.my_Dashboard:

                        if (!(BaseActivity.this instanceof DashboardActivity)) {
                            Intent reset = new Intent();
                            reset.setAction(AppConstants.RESET_FEEDS);
                            sendBroadcast(reset);
                            finish();
                        }
                        preference.removeFromPreference(Preference.PostCount);
                        break;
                    case R.id.ItContactus:
                        if (!(BaseActivity.this instanceof ContactUsActivity)) {
                            intent = new Intent(BaseActivity.this, ContactUsActivity.class);
                        }
                        break;
                    case R.id.settings:
                        if (!(BaseActivity.this instanceof SettingActivity)) {
                            intent = new Intent(BaseActivity.this, SettingActivity.class);
                        }
                        break;
                    case R.id.TandC:
                        if (!(BaseActivity.this instanceof TermsandCondition)) {
                            intent = new Intent(BaseActivity.this, TermsandCondition.class);
                        }
                        break;
                 /*   case R.id.Chat:
                        if (!(BaseActivity.this instanceof ChatActivity))
                            intent = new Intent(BaseActivity.this, ChatActivity.class);
                        break;*/
                    case R.id.Inbox:
                        if (!(BaseActivity.this instanceof InboxActivity)) {
                            intent = new Intent(BaseActivity.this, InboxActivity.class);
                        }
                        preference.removeFromPreference(Preference.NotificationCount);
                        break;
                    case R.id.my_profile:
                        if (!(BaseActivity.this instanceof MyProfileActivity)) {
                            intent = new Intent(BaseActivity.this, MyProfileActivity.class);
                            intent.putExtra("isFrom", "menu");
                        }
/*
                        if (!(BaseActivity.this instanceof MyProfileActivity)) {
                            intent = new Intent(BaseActivity.this, MyProfileActivity.class);
                            intent.putExtra("isFrom", "menu");
                        }
*/
                        break;
                    case R.id.logout:
                        if (isNetworkConnectionAvailable()) {
//                            if (!(BaseActivity.this instanceof UserNavigationActivity)) {
                            if (!(BaseActivity.this instanceof LoginActivity)) {
                                clearPreference();
                                AppConstants.LoginCount = 0;
                                intent = new Intent(BaseActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("From", "menu");
                            }
                        } else {
                            showCustomDialog(BaseActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", "", false);
                        }
                        break;
                }

                if (intent != null) {
                    overridePendingTransition(R.anim.slide_right1, R.anim.slide_left1);
                    if (BaseActivity.this instanceof DashboardActivity)
                        startActivity(intent);
                    else {
                        finish();
                        startActivity(intent);
                    }
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    //******************************************************************** App Bar End **************************************************/

    //******************************************************************** Fab Contorls Start **************************************************/

    private void setFabVisiblity() {
        if (BaseActivity.this instanceof DashboardActivity) {
            fab.setVisibility(View.VISIBLE);
        } else
            fab.setVisibility(View.GONE);
    }

    @OnClick(R.id.fab)
    public void performFabAction() {
        Toast.makeText(BaseActivity.this, "No Actions to perform.", Toast.LENGTH_LONG).show();
    }

    //******************************************************************** Fab Contorls Start **************************************************/


    //******************************************************************** TypeFace Start **************************************************/
    private void setTypeFace(ViewGroup group) {
        IKonnectApplication.setTypeFace(group, AppConstants.REGULAR);
        setTypeFace();
    }

    protected abstract void setTypeFace();
    //******************************************************************** TypeFace End **************************************************/

    public void showSnackMessage(String message) {
        Snackbar snackbar = Snackbar.make(flBody, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public boolean checkNetworkConnection() {
        if (!NetworkUtility.isNetworkConnectionAvailable(BaseActivity.this)) {
            showCustomDialog(BaseActivity.this, getString(R.string.alert), getString(R.string.No_Network_connection), getString(R.string.OK), "", AppConstants.INTERNET_ERROR, false);
            return false;
        }
        return true;
    }

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clearPreference() {
        preference.removeFromPreference(Preference.IS_LOGGED_IN
        );
    }

    public Preference getPreference() {
        return preference;
    }

    public void setLocale(String language) {
        if (StringUtils.isEmpty(language))
            language = "en";
        preference.saveStringInPreference(Preference.LANGUAGE, language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public int getStatusBarHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    @SuppressLint("NewApi")
    public int getSoftbuttonsbarHeight() {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        } else {
            return getStatusBarHeight();
        }
    }

    public void showAlert(String message, String from) {
        showCustomDialog(BaseActivity.this, getString(R.string.alert), message, getString(R.string.OK), "", from);
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from) {
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true));
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
        runOnUiThread(new ShowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, isCancelable));
    }

    // For showing Dialog message.
    private class ShowCustomDialogs implements Runnable {
        private CustomDialog customDialog;
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private boolean isCancelable = false;
        private View.OnClickListener posClickListener;
        private View.OnClickListener negClickListener;

        public ShowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!isFinishing()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle(strTitle);
                    TextView myMsg = new TextView(BaseActivity.this);
                    myMsg.setTextColor(getResources().getColor(R.color.text_color_dark));
                    myMsg.setText(strMessage);
                    myMsg.setGravity(Gravity.LEFT);
                    myMsg.setPadding(50, 15, 30, 0);
                  /*  if (preference.getStringFromPreference(Preference.LANGUAGE, "").equalsIgnoreCase(AppConstants.ENGLISH)) {
                        myMsg.setPadding(60, 15, 0, 0);//left,top,right,bottom
                        myMsg.setGravity(Gravity.LEFT);
                    } else {
                        myMsg.setPadding(0, 15, 40, 0);
                        myMsg.setGravity(Gravity.RIGHT);
                    }*/
                    builder.setView(myMsg);
                    alertDialog.setView(myMsg);
                    alertDialog.setCancelable(isCancelable);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, firstBtnName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onButtonYesClick(from);
                        }
                    });
                    if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, secondBtnName, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onButtonNoClick(from);
                            }
                        });
                    }
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_color));
                            Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                            if (button != null) {
                                button.setTextColor(getResources().getColor(R.color.app_color));
                            }
                        }
                    });
                    alertDialog.show();
                }
            } else {

                try {
                    if (customDialog != null && customDialog.isShowing())


                        customDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                customDialog = null;

                View view = inflater.inflate(R.layout.custom_popup, null);
                customDialog = new CustomDialog(BaseActivity.this, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                customDialog.setCancelable(false);

                TextView tvAlert = (TextView) view.findViewById(R.id.tvAlert);
                TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
                Button btnYes = (Button) view.findViewById(R.id.btnYes);
                Button btnNo = (Button) view.findViewById(R.id.btnNo);

                btnYes.setText(firstBtnName);
                tvAlert.setText(strTitle);
                tvMessage.setText(strMessage);

                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
                    btnNo.setEnabled(true);
                    btnNo.setClickable(true);
                    btnNo.setText(secondBtnName);
                }
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        hideLoader();
                        onButtonYesClick(from);
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        onButtonNoClick(from);
                        customDialog.dismiss();
                    }
                });

                try {
                    if (customDialog != null && !customDialog.isShowing())
                        customDialog.showCustomDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onButtonYesClick(String from) {
        if (from.equalsIgnoreCase("LOGOUT")) {
            clearPreference();
            AppConstants.LoginCount = 0;
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (from.equalsIgnoreCase("finish")) {
            finish();
        }
        if (from.equalsIgnoreCase("gotoDashboard"))
            finish();

        else if (from.equalsIgnoreCase("forcelogout")) {
            clearPreference();
            AppConstants.LoginCount = 0;
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("From", "menu");
            startActivity(intent);
        }

        if (from.equalsIgnoreCase("CardCancel")) {

        }
    }

    public void onButtonNoClick(String from) {

    }

    public void showDatePickerDialog(final Context context, final TextView tvDate) {
        int year;
        int month;
        int day;
        final int dayOfWeek;
        final Calendar c = Calendar.getInstance();
        if (StringUtils.isEmpty((String) tvDate.getTag(R.string.DATE))) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        } else {
            int[] dates=null;
            String date = (String) tvDate.getTag(R.string.DATE);
            if (BaseActivity.this instanceof LeaveApplicationActivtiy){
                dates = CalendarUtil.getCurrentDayMonthYear(date, FULL_DATE_FORMATE);
            }else {
                dates = CalendarUtil.getCurrentDayMonthYear(date, CalendarUtil.DD_MMM_YYYY_PATTERN);
            }
            year = dates[2];
            month = dates[1];
            day = dates[0];
            dayOfWeek=dates[3];
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Log.i("Date", year + "/" + month + "/" + dayOfMonth);
                //  tvDate.setTag(R.string.DATE, CalendarUtil.getDate(dayOfMonth, month, year, CalendarUtil.DD_MMM_YYYY_PATTERN, Locale.getDefault()));

                if (BaseActivity.this instanceof LeaveApplicationActivtiy){
                    tvDate.setText(CalendarUtil.getFullDate(dayOfWeek,dayOfMonth, month, year, FULL_DATE_FORMATE, Locale.getDefault()));
                }else {
                    tvDate.setText(CalendarUtil.getDate(dayOfMonth, month, year, CalendarUtil.DD_MMM_YYYY_PATTERN, Locale.getDefault()));
                }

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private String userChoosenTask;

    private int REQUEST_CAMERA = 100, SELECT_FILE = 101, SELECT_DOCUMENT = 102;

    public void CaptureImage() {
        final CharSequence[] items = {getString(R.string.Take_Photo), getString(R.string.Choose_from_Gallery), getString(R.string.Documents),
                getString(R.string.Cancel_small)};

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.Take_Photo))) {
                    userChoosenTask = getString(R.string.Take_Photo);
//                    if(result)
                    cameraIntent();

                } else if (items[item].equals(getString(R.string.Choose_from_Gallery))) {
                    userChoosenTask = getString(R.string.Choose_from_Gallery);
//                    if(result)
                    galleryIntent();

                } else if (items[item].equals(getString(R.string.Documents))) {
                    userChoosenTask = getString(R.string.Documents);
//                    if(result)
                    documentsIntent();

                } else if (items[item].equals(getString(R.string.Cancel_small))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void documentsIntent() {

        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, SELECT_DOCUMENT);

    }

    private void galleryIntent() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        startActivityForResult(intent,SELECT_FILE);
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
//            intent.setType("image*/");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(intent, SELECT_FILE);
        }
    }

    private void cameraIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if (isDeviceSupportCamera()) {
                File file = AppConstants.getOutputImageFile("StoreImage");
                if (file != null) {
                    capturedImagePath = file.getAbsolutePath();
                    Uri fileUri = Uri.fromFile(file);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent.putExtra("fileName", file.getName());
                    intent.putExtra("filePath", file.getAbsolutePath());
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
            } else {
                //No Camera
                showSnackMessage("Device does not support camera");
            }
        } else {
            launchCameraforN();
        }


    }

    private void launchCameraforN() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = AppConstants.getOutputImageFile("StoreImage");
        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        capturedImagePath = file.getAbsolutePath();
        intent.putExtra("fileName", file.getName());
        intent.putExtra("filePath", file.getAbsolutePath());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }


    private boolean isDeviceSupportCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            } else if (requestCode == SELECT_DOCUMENT) {
                onSelectDocumetFromGalleryResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

//        Bitmap thumbnail = BitmapFactory.decodeFile(capturedImagePath);
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ivImage.setImageBitmap(thumbnail);

        setAttachment(capturedImagePath, null);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
//        String[] filePathColumn = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//        if(cursor.moveToFirst())
//        {
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            capturedImagePath = cursor.getString(columnIndex);
//            cursor.close();
//        }
//        postProfilePicture(capturedImagePath);
        setAttachment(getPath(BaseActivity.this, selectedImage), null);
    }

    private void onSelectDocumetFromGalleryResult(Intent data) {

//        Bitmap bm=null;
//        if (data != null) {
//            try {
//                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        data.getDataString();
        Uri selectedImage = data.getData();
//        String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//        Cursor cursor = getContentResolver().query(selectedImage,
//                filePathColumn, null, null, null);
//        if(cursor.moveToFirst())
//        {
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            capturedImagePath = cursor.getString(columnIndex);
//            cursor.close();
//        }
//        postProfilePicture(capturedImagePath);
        setAttachment(getPath(BaseActivity.this, selectedImage), null);
    }

    public Bitmap getThumbnail(String path) throws Exception {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns._ID}, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
        }
        cursor.close();
        return null;
    }

    public void showToast(String message) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * Loader Custom
     */

    protected Dialog dialog;
    private Animation rotateXaxis;
    private ImageView ivOutsideImage, ImageView01;
    public boolean isCancelableLoader;

    public void showLoader(String str) {
        runOnUiThread(new RunShowLoaderCustom(str));
    }

    public void showLoader(String msg, String title) {
        runOnUiThread(new RunShowLoaderCustom(msg, title));
    }

    /**
     * Name:         RunShowLoader
     * Description:  This is to show the loading progress dialog when some other functionality is taking place.
     **/
    class RunShowLoaderCustom implements Runnable {
        private String title;
        private String strMsg;

        public RunShowLoaderCustom(String strMsg) {
            this.strMsg = strMsg;
        }

        public RunShowLoaderCustom(String strMsg, String title) {
            this.strMsg = strMsg;
            this.title = title;
        }

        @Override
        public void run() {
            try {
                if (dialog == null)
                    dialog = new Dialog(BaseActivity.this, R.style.Theme_Dialog_Translucent);

                dialog.setContentView(R.layout.loading);

                if (!isCancelableLoader)
                    dialog.setCancelable(false);
                else
                    dialog.setCancelable(true);


                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ivOutsideImage = (ImageView) dialog.findViewById(R.id.ivOutsideImage);
                ImageView01 = (ImageView) dialog.findViewById(R.id.ImageView01);

                TextView tvLoading = (TextView) dialog.findViewById(R.id.tvLoading);
                if (!strMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strMsg);
                else
                    tvLoading.setVisibility(View.GONE);
                rotateXaxis = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.rotate_x_axis);
                rotateXaxis.setInterpolator(new LinearInterpolator());

                ivOutsideImage.setAnimation(rotateXaxis);
//                applyRotation(0, 360);
            } catch (Exception e) {
            }
        }
    }

    private void applyRotation(final float start, final float end) {

        BitmapDrawable bd = (BitmapDrawable) BaseActivity.this.getResources().getDrawable(R.drawable.progress_mid_icon);
        float centerY = bd.getBitmap().getHeight() / 2.0f;
        float centerX = bd.getBitmap().getWidth() / 2.0f;

        final Flip3dAnimation rotation = new Flip3dAnimation(start, end, centerX, centerY);
        rotation.setDuration(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new LinearInterpolator());
        ImageView01.startAnimation(rotation);
    }


    /**
     * Name:         RunShowLoader
     * Description:  For hiding progress dialog (Loader ).
     **/
    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    CustomDialog mCustomDialog;

    public void showFormSubmitPopup(String titleNAme, String message) {
        try {
            if (mCustomDialog != null && mCustomDialog.isShowing())
                mCustomDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCustomDialog = null;

        View view = inflater.inflate(R.layout.custom_popup_trx_complete, null);
        mCustomDialog = new CustomDialog(BaseActivity.this, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mCustomDialog.setCancelable(false);

        TextView tv_formTitle = (TextView) view.findViewById(R.id.tv_formTitle);
        TextView tv_responseTitle = (TextView) view.findViewById(R.id.tv_responseTitle);
        Button btn_proceed = (Button) view.findViewById(R.id.btn_proceed);

        tv_formTitle.setText(titleNAme);
        tv_responseTitle.setText(message);


        btn_proceed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
                Intent intent = new Intent(BaseActivity.this, TrackServiceDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("FORMS", "FORMS");
                startActivity(intent);
                finish();
                /*Intent intent1 = new Intent();
                intent.setAction(AppConstants.HR_SERVICES);
                sendBroadcast(intent);*/
            }
        });

        try {
            if (mCustomDialog != null && !mCustomDialog.isShowing())
                mCustomDialog.showCustomDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CustomDialog attachmentsDialog;
    public Button Ok,Cancel;
    public void showAttachmentsPopup(String titleName, String message) {
        try {
            if (attachmentsDialog != null && attachmentsDialog.isShowing())
                attachmentsDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        attachmentsDialog = null;

        View view = inflater.inflate(R.layout.attachments_popup, null);
        attachmentsDialog = new CustomDialog(BaseActivity.this, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        attachmentsDialog.setCancelable(false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
          Ok = (Button) view.findViewById(R.id.Ok);
          Cancel = (Button) view.findViewById(R.id.Cancel);

        tvTitle.setText(titleName);
        tvMessage.setText(message);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attachmentsDialog.dismiss();
            }
        });

        try {
            if (attachmentsDialog != null && !attachmentsDialog.isShowing())
                attachmentsDialog.showCustomDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chatCountUtils != null)
            chatCountUtils.removeChatListner();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isBackAllowed) {
            Intent intent = new Intent();
            intent.setAction(AppConstants.RESET_FEEDS);
            sendBroadcast(intent);
        }

    }


    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void CaptureImageForProfile() {
//        final CharSequence[] items = { "Take Photo", "Choose from Gallery", "Cancel" };
        final CharSequence[] items = {getString(R.string.Take_Photo), getString(R.string.Choose_from_Gallery), getString(R.string.Cancel_small)};

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.Take_Photo))) {
                    userChoosenTask = "Take Photo";
//                    if(result)
                    cameraIntent();

                } else if (items[item].equals(getString(R.string.Choose_from_Gallery))) {
                    userChoosenTask = "Choose from Library";
//                    if(result)
                    galleryIntent();

                } else if (items[item].equals(getString(R.string.Cancel_small))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public interface Predicate<T> {
        boolean apply(T type);
    }

    public static <T> Collection<T> filter(Collection<T> col,
                                           Predicate<T> predicate) {

        Collection<T> result = new ArrayList<T>();
        if (col != null) {
            for (T element : col) {
                if (predicate.apply(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    protected boolean isNetworkConnectionAvailable() {
        // checking the Internet availability
        boolean isNetworkConnectionAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            isNetworkConnectionAvailable = activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
        }

        return isNetworkConnectionAvailable;
    }


    //newlay added by sandeep

    public void getSelectedDateFromDatePicker(Context context, final TextView tvDate) {


        int year;
        int month;
        int day;
        final Calendar c = Calendar.getInstance();
      /*  year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);*/


        if (StringUtils.isEmpty((String) tvDate.getTag(R.string.DATE))) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String date = (String) tvDate.getTag(R.string.DATE);
            int[] dates = CalendarUtil.getCurrentDayMonthYear(date, DD_MMM_YYYY_PATTERN);
            year = dates[2];
            month = dates[1];
            day = dates[0];
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                tvDate.setText(CalendarUtil.getDate(dayOfMonth, month, year, DD_MMM_YYYY_PATTERN, Locale.getDefault()) + "");
            }
        }, year, month, day);

         if (context instanceof LeaveJoining || context instanceof VisitVisaForSpouseChildren ||context instanceof VisitVisaForinLawsChildren
                ||context instanceof FamilyVisaJoiningForSpouseChildren || context instanceof FamilyVisaJoiningForParents || context instanceof SalaryTransferLetterRequest
                ||context instanceof CardStatementActivity ||context instanceof AtmSwitchClaimActivity)
        {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        datePickerDialog.show();


    }


    public void getSelectedDateFromDatePicker(Context context, final String startDate, final TextView endDate) {


        int year;
        int month;
        int day;
        long millisecondsFromNow = 0;
        long milliseconds = 0;
        final Calendar c = Calendar.getInstance();

        if (StringUtils.isEmpty((startDate))) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            String input = startDate.toString();
            int[] dates = CalendarUtil.getCurrentDayMonthYear(input, DD_MMM_YYYY_PATTERN);
            year = dates[2];
            month = dates[1];
            day = dates[0];
            try {
                Date date = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH).parse(input);
                milliseconds = date.getTime();
                millisecondsFromNow = milliseconds - (new Date()).getTime();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                {
                    endDate.setText(CalendarUtil.getDate(dayOfMonth, month, year, DD_MMM_YYYY_PATTERN, Locale.getDefault()) + "");

                 /*   if (BaseActivity.this instanceof LeaveApplicationActivtiy) {
                        salaryInAdvance = findDateDifference(startDate, endDate.getText().toString());
                    }*/

                }

            }
        }, year, month, day + 1);
        datePickerDialog.getDatePicker().setMinDate(milliseconds + 1000);

        datePickerDialog.show();


    }


    public void getSelectedDateFromDatePicker(Context context, final String startDate, final TextView endDate, final TextView tv_salaryView) {


        int year;
        int month;
        int day;
        final int dayOfWeek;
        long millisecondsFromNow = 0;
        long milliseconds = 0;
        final Calendar c = Calendar.getInstance();

        if (StringUtils.isEmpty((startDate))) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        } else {
            String input = startDate.toString();
            int[] dates = CalendarUtil.getCurrentDayMonthYear(input, FULL_DATE_FORMATE);
            year = dates[2];
            month = dates[1];
            day = dates[0];
            dayOfWeek = dates[3];

            try {
                Date date = new SimpleDateFormat(FULL_DATE_FORMATE, Locale.ENGLISH).parse(input);
                milliseconds = date.getTime();
                millisecondsFromNow = milliseconds - (new Date()).getTime();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                {
                    endDate.setText(CalendarUtil.getFullDate(dayOfWeek,dayOfMonth, month, year, FULL_DATE_FORMATE, Locale.getDefault()) + "");

                    if (!StringUtils.isEmpty(endDate.getText().toString())) {

                        String strBand = preference.getStringFromPreference(Preference.BAND, "");

                        if (strBand.equalsIgnoreCase("B3") || strBand.equalsIgnoreCase("B4") ||
                                strBand.equalsIgnoreCase("B5") || strBand.equalsIgnoreCase("B6")) {
                            tv_salaryView.setText((findDateDifference(startDate, endDate.getText().toString()) - findTwoDaysCount(startDate, endDate.getText().toString(), Calendar.FRIDAY, Calendar.SATURDAY)) + " Days");

                        } else {
                            tv_salaryView.setText((findDateDifference(startDate, endDate.getText().toString()) + " Days"));
                        }
                    }


                }

            }
        }, year, month, day + 1);
        datePickerDialog.getDatePicker().setMinDate(milliseconds + 1000);

        datePickerDialog.show();


    }


    public CustomDialog mCustomDialogSign;
    public SignatureView customerSignature;
    public Button btnOK, btnCancle, btnClear;

    public void showSignatureDialog() {
        try {
            if (mCustomDialogSign != null && mCustomDialogSign.isShowing())
                mCustomDialogSign.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCustomDialogSign = null;
        View view = inflater.inflate(R.layout.custom_popup_signature, null);
        mCustomDialogSign = new CustomDialog(BaseActivity.this, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        mCustomDialogSign.setCancelable(false);

        LinearLayout llSign = (LinearLayout) view.findViewById(R.id.llSign);
        btnCancle = (Button) view.findViewById(R.id.btnCancle);
        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnClear = (Button) view.findViewById(R.id.btnClear);


        customerSignature = new SignatureView(this);
        customerSignature.setDrawingCacheEnabled(true);
        customerSignature.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (180 * px)));
        customerSignature
                .setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        llSign.addView(customerSignature);


        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideLoader();
                if (customerSignature != null)
                    mCustomDialogSign.dismiss();
//
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerSignature.resetSign();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!customerSignature.isSigned()) {
                    mCustomDialogSign.dismiss();
                    showCustomDialog(getApplicationContext(), "Alert !",
                            "Signature is mandatory.",
                            getString(R.string.OK), null, "scroll");
                } else {
                    postSignature();
                }


            }
        });

        try {
            if (mCustomDialogSign != null && !mCustomDialogSign.isShowing())
                mCustomDialogSign.showCustomDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void postSignature() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap image = getBitmap(customerSignature);
                ByteArrayOutputStream streams = new ByteArrayOutputStream();
                if (image != null) {
//                    image.compress(Bitmap.CompressFormat.JPEG, 100, streams);

                    storeImage(image, AppConstants.SIGNATURE);
                }
            }
        }).start();
    }


    public void storeImage(Bitmap imageData, final String filename) {
        try {
            File dir = getSignatureImageFile("Signature");
            imageData = Bitmap.createScaledBitmap(imageData, 640, 480, false);
//            File file = new File(dir, "Signature.png");
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(dir);
                imageData.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            preference.saveStringInPreference(filename, dir.getAbsolutePath());
            preference.commitPreference();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Bitmap getBitmap(SignatureView myView) {

        if (myView != null) {
            Bitmap bitmap = myView.getDrawingCache(true);
            return bitmap;
        }

        return null;
    }


    CustomDialog customDialog;
    private VisitVisaAdapter visitVisaAdapter;
    private ServiceDO.ServiceType type;

    public void showFormPopup(final String key, ArrayList<ServiceDO> arrForms) {
        try {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        customDialog = null;

        View view = inflater.inflate(R.layout.show_form_popup, null);
        customDialog = new CustomDialog(BaseActivity.this, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        customDialog.setCancelable(false);

        RecyclerView rvForm = (RecyclerView) view.findViewById(R.id.rvForm);
        rvForm.setLayoutManager(new LinearLayoutManager(this));

        if (key.equalsIgnoreCase(getResources().getString(R.string.Bank_Account_Update))) {
            type = BANK_ACCOUNT_UPDATE;
        } else if (key.equalsIgnoreCase(getResources().getString(R.string.HR_Service_Request_For_Leave))) {
            type = HR_SERVICE_REQUEST;
        } else if (key.equalsIgnoreCase(getResources().getString(R.string.Visit_Visa))) {
            type = VISIT_VISA;
        } else if (key.equalsIgnoreCase(getResources().getString(R.string.Salary_Request))) {
            type = SALARY_REQUEST;
        }

        visitVisaAdapter = new VisitVisaAdapter(arrForms);
        rvForm.setAdapter(visitVisaAdapter);

        try {
            if (customDialog != null && !customDialog.isShowing())
                customDialog.showCustomDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

        customDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if (keyCode == keyEvent.KEYCODE_BACK) {
                    try {
                        if (customDialog != null && customDialog.isShowing())
                            customDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    private class VisitVisaAdapter extends RecyclerView.Adapter<VisitVisaAdapter.MyViewHolder> {

        private ArrayList<ServiceDO> arrForms;

        public VisitVisaAdapter(ArrayList<ServiceDO> arrForms) {
            this.arrForms = arrForms;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);
            return new VisitVisaAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
            final ServiceDO serviceDO = arrForms.get(position);
            myViewHolder.tvFormName.setText(serviceDO.serviceName);

            myViewHolder.tvFormName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (type) {
                        case BANK_ACCOUNT_UPDATE:
                            switch (serviceDO.serviceType) {
                                case SALARY_TRANSFER_REQUEST:
                                    intent = new Intent(getApplicationContext(), SalaryTransferRequestBankAccount.class);
                                    break;
                                case C3_CARD_ATM_WITCH_CLAIM_SERVICE_REQUEST:
                                    intent = new Intent(getApplicationContext(), AtmSwitchClaimActivity.class);
                                    break;
                                case C3_CARD_REPLACEMENT_SERVICE_REQUEST:
                                    intent = new Intent(getApplicationContext(), CardReplacementActivity.class);
                                    break;
                                case C3_CARD_CANCELLATION_SERVICE_REQUEST:
                                    intent = new Intent(getApplicationContext(), CardCancellationActivity.class);
                                    break;
                                case C3_CARD_STATEMENT_SERVICE_REQUEST:
                                    intent = new Intent(getApplicationContext(), CardStatementActivity.class);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case HR_SERVICE_REQUEST:
                            switch (serviceDO.serviceType) {
                                case LEAVE_APPLICATION:
                                    intent = new Intent(getApplicationContext(), LeaveApplicationActivtiy.class);
                                    break;
                                case LEAVE_JOINING:
                                    intent = new Intent(getApplicationContext(), LeaveJoining.class);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case SALARY_REQUEST:
                            switch (serviceDO.serviceType) {

                                case SALARY_CERTIFICATE_REQUEST:
                                    intent = new Intent(getApplicationContext(), SalaryCertificateRequestForm.class);
                                    break;
                                case SALARY_TRANSFER_LETTER_REQUEST:
                                    intent = new Intent(getApplicationContext(), SalaryTransferLetterRequest.class);
                                    break;

                                default:
                                    break;
                            }
                            break;
                        case VISIT_VISA:


                            switch (serviceDO.serviceType) {
                                case VISIT_VISA_FOR_IN_LAWS_CHILDREN:
                                    intent = new Intent(getApplicationContext(), VisitVisaForinLawsChildren.class);
                                    break;
                                case FAMILY_JOINING_VISA_FOR_PARENTS:
                                    intent = new Intent(getApplicationContext(), FamilyVisaJoiningForParents.class);
                                    break;
                                case VISIT_VISA_FOR_SPOUSE_CHILDREN:
                                    intent = new Intent(getApplicationContext(), VisitVisaForSpouseChildren.class);
                                    break;
                                case FAMILY_JOINING_VISA_FOR_SPOUSE_CHILDREN:
                                    intent = new Intent(getApplicationContext(), FamilyVisaJoiningForSpouseChildren.class);
                                    break;

                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    if (intent != null) {
                        intent.putExtra(ConstantExtraKey.SERVICE_OBJECT, serviceDO);
                        startActivity(intent);
                        customDialog.dismiss();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (arrForms != null && arrForms.size() > 0) {
                return arrForms.size();
            } else {
                return 0;
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvFormName;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvFormName = (TextView) itemView.findViewById(R.id.tvFormName);
            }
        }
    }

    public static int findDateDifference(String startDate, String endDate) {
        SimpleDateFormat dfDate = new SimpleDateFormat(FULL_DATE_FORMATE);
        java.util.Date d = null;
        java.util.Date d1 = null;
        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(endDate);
            //  d1 = dfDate.parse(dfDate.format(cal.getTime()));//Returns 15/10/2012
            d1 = dfDate.parse(startDate);//Returns 15/10/2012
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        int diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

        return diffInDays + 1;

    }


    //This method disable all the child of given layout
    public void disableAllChilds(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableAllChilds((ViewGroup) child);
                //setting text color for entire layout
                if (child instanceof TextView)
                    ((TextView) child).setTextColor(getResources().getColor(R.color.text_dark));
                else if (child instanceof EditText)
                    ((EditText) child).setTextColor(getResources().getColor(R.color.text_dark));

            } else {
                child.setEnabled(false);
            }
        }
    }

}
