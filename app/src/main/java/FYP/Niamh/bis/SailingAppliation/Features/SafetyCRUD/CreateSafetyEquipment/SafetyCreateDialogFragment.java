package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import FYP.Niamh.bis.SailingAppliation.Database.DatabaseQueryClass;
import FYP.Niamh.bis.SailingAppliation.R;
import FYP.Niamh.bis.SailingAppliation.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class SafetyCreateDialogFragment extends DialogFragment {

    private static SafetyCreateListener safetyCreateListener;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText descriptionEditText;
    private EditText issueEditText;
    private EditText availableEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private String descriptionString = "";
    private String issueString = "";
    private String availableString;

    public SafetyCreateDialogFragment() {
        // Required empty public constructor
    }

    public static SafetyCreateDialogFragment newInstance(String title, SafetyCreateListener listener){
        safetyCreateListener = listener;
        SafetyCreateDialogFragment safetyCreateDialogFragment = new SafetyCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        safetyCreateDialogFragment.setArguments(args);

        safetyCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return safetyCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_safety_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.safetyNameEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        issueEditText = view.findViewById(R.id.issueEditText);
        availableEditText = view.findViewById(R.id.availableEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(view12 -> {
            nameString = nameEditText.getText().toString();
            descriptionString = descriptionEditText.getText().toString();
            issueString = issueEditText.getText().toString();
            availableString = availableEditText.getText().toString();

            Safety safety = new Safety(-1, nameString, descriptionString, issueString, availableString);

            DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

            long id = databaseQueryClass.insertSafety(safety);

            if(id>0){
                safety.setId(id);
                safetyCreateListener.onSafetyCreated(safety);
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(view1 -> getDialog().dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
