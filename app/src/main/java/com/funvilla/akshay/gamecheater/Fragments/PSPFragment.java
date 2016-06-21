package com.funvilla.akshay.gamecheater.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.funvilla.akshay.gamecheater.Adapter.CustomListAdapter;
import com.funvilla.akshay.gamecheater.Database.DBValues;
import com.funvilla.akshay.gamecheater.Database.DataTable;
import com.funvilla.akshay.gamecheater.RetrofitFiles.EndPoints;
import com.funvilla.akshay.gamecheater.RetrofitFiles.GameListAPI;
import com.funvilla.akshay.gamecheater.RetrofitFiles.GameTitleList;
import com.funvilla.akshay.gamecheater.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Akshay on 2/22/2016.
 */
public class PSPFragment extends com.funvilla.akshay.gamecheater.Fragments.BaseFragment implements View.OnClickListener, TextWatcher {
    View view;
    int textChangeCount = 0;
    public static final String GAME_LIST_URL = "http://funvilla.in/";
    TextInputLayout inputLayout;
    String newTrimString = "";
    ArrayList<String> trimTextCont = new ArrayList<>();
    EditText gameET;
    Boolean isListLoaded = false;
    ArrayList<String> gameList;
    ListView gameListView;
    ProgressDialog Progdialog;
    int indexCount = 0;
    String changedText;
    public ArrayList<String> cheat = new ArrayList<>();
    ArrayList<String> newList = new ArrayList<String>();
    Button reqBT;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText reqGameET;
    TextInputLayout inputLayoutDialog;
    Dialog dialog;
    public static final String PREFS_NAME = "list_pref";
    private int timeCounter = 0;
    ArrayList<Integer> PositionContainer = new ArrayList<>();
    ProgressDialog reqSendProgress;
    private java.lang.String platform = "";
    private Spinner reqGameSpinner;
    private ProgressBar dottedPB;
    private TextView dottedTV;

    int counter = 0;
    ContentValues insertContent;
    int count = 0;
    DataTable table;

    SharedPreferences listBool;
    SharedPreferences.Editor listBoolEditor;
    public static final String LISTBOOL_PREF = "list_bool_pref";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Progdialog = new ProgressDialog(activity);
        Log.e("***TAG***", "onCreate");
        table = new DataTable(DBValues.PSP_TABLE);
        insertContent= new ContentValues();
        listBool = activity.getSharedPreferences(LISTBOOL_PREF,Context.MODE_PRIVATE);
        listBoolEditor = listBool.edit();

        preferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        isListLoaded = preferences.getBoolean("isListLoaded", false);
        if (gameList == null) {
            isListLoaded = false;
            editor.putBoolean("isListLoaded", false).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("***TAG***", "onCreateView");
        activity.getSupportActionBar().setTitle("PSP Cheater");
        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        view = inflater.inflate(R.layout.fragment_listview, null);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textChangeCount = 0;
        init();
        reqBT.setOnClickListener(this);
        if (!listBool.contains("isPSP")) {
        //if (!isListLoaded && gameList == null) {
            Progdialog.setTitle("Please Wait");
            Progdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    activity.coloredToast("\"If list takes too much time to load then check your internet connection\"");
                }
            });
            Progdialog.setMessage("Loading Game List");

            Progdialog.show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    timeCounter++;
                    if (isListLoaded) {
                        handler.removeCallbacks(this);
                    } else {
                        if (timeCounter == 20) {
                            handler.removeCallbacks(this);

                           activity.coloredToast("\"Slow Internet Connection.\\List isn't loaded yet. Please wait\"");
                        }
                    }
                    handler.postDelayed(this, 1000);
                }
            }, 1000);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(GAME_LIST_URL).addConverterFactory(GsonConverterFactory.create()).build();
            GameListAPI api = retrofit.create(GameListAPI.class);
            Call<GameTitleList> listCall = api.loadData(EndPoints.PSP);
            listCall.enqueue(new Callback<GameTitleList>() {

                @Override
                public void onResponse(final Response<GameTitleList> response, Retrofit retrofit) {
                    gameList = new ArrayList<String>();

                    for (int i = 0; i < response.body().getHolders().size(); i++) {
                        insertContent = new ContentValues();

                        if (response.body().getHolders().get(i).getCheat() != "" && response.body().getHolders().get(i).getTitle() != "") {
                            cheat.add(response.body().getHolders().get(i).getCheat());
                            gameList.add(i, response.body().getHolders().get(i).getTitle());
                            insertContent.put(DBValues.COL_3_cheat, cheat.get(i));
                            insertContent.put(DBValues.COL_2_title, gameList.get(i));


                            new SQLiteDataLoader(insertContent, activity.helper, table).execute();
                            Log.e("I ", "" + i);
                        }


                    }
                    isListLoaded = true;
                    editor.putBoolean("isListLoaded", true).commit();
                    listBoolEditor.putBoolean("isPSP", true).commit();
                    gameListView.setAdapter(new CustomListAdapter(activity, R.layout.title_container, gameList, reqBT, gameListView, activity, cheat, PositionContainer));

                    Progdialog.dismiss();

                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });

        }else
        {
            gameList = new ArrayList<>();
            Cursor cursor =  table.retrieve(activity.helper.getWritableDatabase());
            isListLoaded = true;
            if(cursor != null)
            {
                int count = cursor.getCount();
                for (int i = 0 ; i < count ; i++) {
                    if(cursor.moveToNext()){
                        gameList.add(i,cursor.getString(1));
                        cheat.add(i,cursor.getString(2));
                    }

                }
            }


        }

        if (gameList != null) {
            gameListView.setAdapter(new CustomListAdapter(activity, R.layout.title_container, gameList, reqBT, gameListView, activity, cheat, PositionContainer));
        }
        gameET.addTextChangedListener(this);


        return view;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (gameList != null) {
            indexCount = 0;
            changedText = "" + s;
            newList.clear();
            PositionContainer.clear();


            if (s.length() < 30) {
                for (int i = 0; i < gameList.size(); i++) {
                    for (int j = 0; j < s.length(); j++) {
                        if ((gameList.get(i).length() >= s.length())) {
                            newTrimString = newTrimString + String.valueOf(gameList.get(i).charAt(j));
                        }
                    }


                    trimTextCont.add(i, newTrimString);
                    newTrimString = "";

                    if ((trimTextCont.get(i).equalsIgnoreCase(changedText.toString()))) {
                        newList.add(indexCount, gameList.get(i));
                        PositionContainer.add(indexCount, gameList.indexOf(gameList.get(i)));
                        indexCount += 1;
                    }
                }
                trimTextCont.clear();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (gameList != null) {
            gameListView.setAdapter(new CustomListAdapter(activity, R.layout.title_container, newList, reqBT, gameListView, activity, cheat, PositionContainer));
        }
    }


    private void init() {

        inputLayout = (TextInputLayout) view.findViewById(R.id.inputLayout);
        gameET = (EditText) view.findViewById(R.id.gameET);
        gameListView = (ListView) view.findViewById(R.id.titleLV);
        reqBT = (Button) view.findViewById(R.id.reqGameBT);
        reqBT.setVisibility(View.INVISIBLE);
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Game Request");
        inputLayoutDialog = (TextInputLayout) dialog.findViewById(R.id.textInputLayout);
        reqGameET = (EditText) dialog.findViewById(R.id.reqGameET);
        Button reqGame = (Button) dialog.findViewById(R.id.reqGameDialog);
        reqGameSpinner = (Spinner) dialog.findViewById(R.id.gameSelectorSpinner);
        dottedPB = (ProgressBar) view.findViewById(R.id.dottedPB);
        dottedTV = (TextView) view.findViewById(R.id.dottedTV);
        callHandler();
        final ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("PC");
        spinnerItems.add("PS2");
        spinnerItems.add("PS3");
        spinnerItems.add("PS4");
        spinnerItems.add("PSP");
        spinnerItems.add("VITA");
        spinnerItems.add("XBOX 360");
        spinnerItems.add("XBOX ONE");
        spinnerItems.add("Nintendo Wii U");
        spinnerItems.add("Nintendo Wii");
        spinnerItems.add("Nintendo 3DS");
        spinnerItems.add("Nintendo DS");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        reqGameSpinner.setAdapter(spinnerAdapter);
        reqGameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                platform = spinnerItems.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reqGame.setOnClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        editor.clear().commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reqGameBT:
               // gameET.removeTextChangedListener(this);
                dialog.show();

                break;
            case R.id.reqGameDialog:
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://funvilla.in/request.php?platform=" + URLEncoder.encode(platform) + "&name=" + URLEncoder.encode(reqGameET.getText().toString()), new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        reqSendProgress = new ProgressDialog(activity);
                        reqSendProgress.setTitle("Please Wait...");
                        reqSendProgress.setMessage("Sending your request..");
                        reqSendProgress.setCancelable(false);
                        reqSendProgress.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (reqSendProgress.isShowing()) {
                            reqSendProgress.dismiss();
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        gameET.addTextChangedListener(PSPFragment.this);
                        gameListView.setAdapter(new CustomListAdapter(activity, R.layout.title_container, gameList, reqBT, gameListView, activity, cheat, PositionContainer));

                        activity.coloredToast("\"Request has been sucessfully sent\"");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);

                        if (reqSendProgress.isShowing()) {
                            reqSendProgress.dismiss();
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        gameET.setText("");
                        gameET.addTextChangedListener(PSPFragment.this);
                        gameListView.setAdapter(new CustomListAdapter(activity, R.layout.title_container, gameList, reqBT, gameListView, activity, cheat, PositionContainer));
                        activity.coloredToast("\"Something went wrong with connection\"");
                    }
                });
                break;
        }

    }
    private void callHandler() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isListLoaded) {
                    new DottedProgress(dottedPB, dottedTV, isListLoaded, counter, activity).showProgress();
                    handler.removeCallbacks(this);

                } else {
                    if (counter > 5)

                    {
                        counter = 0;
                    }
                    counter++;
                    new DottedProgress(dottedPB, dottedTV, isListLoaded, counter, activity).showProgress();
                }
                handler.postDelayed(this, 500);
            }
        }, 0);
    }
}
