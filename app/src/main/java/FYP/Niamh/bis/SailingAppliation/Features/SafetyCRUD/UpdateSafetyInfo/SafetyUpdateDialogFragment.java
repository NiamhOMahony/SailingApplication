package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.UpdateSafetyInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.DialogFragment;
import FYP.Niamh.bis.SailingAppliation.Database.DatabaseQueryClass;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.Safety;
import FYP.Niamh.bis.SailingAppliation.R;
import FYP.Niamh.bis.SailingAppliation.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class SafetyUpdateDialogFragment extends DialogFragment {

    private static SafetyUpdateListener safetyUpdateListener;
    private static long safetyId;
    private static int position;

    private Safety mSafety;

    private EditText safetyNameEditText;
    private EditText descriptionEditText;
    private EditText issueEditText;
    private EditText availableEditText;
    private Button updateButton;
    private Button cancelButton;

    private String safetyNameString = "";
    private String descriptionString = "";
    private String issueString = "";
    private String availableString = "";


    private DatabaseQueryClass databaseQueryClass;

    public SafetyUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static SafetyUpdateDialogFragment newInstance(long subId, int pos, SafetyUpdateListener listener){
        safetyId = subId;
        position = pos;
        safetyUpdateListener = listener;

        SafetyUpdateDialogFragment safetyUpdateDialogFragment = new SafetyUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update safety equipment information");
        safetyUpdateDialogFragment.setArguments(args);

        safetyUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return safetyUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_update_dialog, container, false);

        safetyNameEditText = view.findViewById(R.id.safetyNameEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        issueEditText = view.findViewById(R.id.issueEditText);
        availableEditText = view.findViewById(R.id.availableEditText);
        updateButton = view.findViewById(R.id.updateSafetyInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mSafety = databaseQueryClass.getSafetyById(safetyId);

        safetyNameEditText.setText(mSafety.getName());
        descriptionEditText.setText(mSafety.getDescription());
        issueEditText.setText(mSafety.getIssue());
        availableEditText.setText(String.valueOf(mSafety.getAvailable()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safetyNameString  = safetyNameEditText.getText().toString();
                descriptionString = descriptionEditText.getText().toString();
                issueString  = issueEditText.getText().toString();
                availableString = availableEditText.getText().toString();

                mSafety.setName(safetyNameString);
                mSafety.setDescription(descriptionString);
                mSafety.setIssue(issueString);
                mSafety.setAvailable(availableString);

                long id = databaseQueryClass.updateSafetyInfo(mSafety);

                if(id>0){
                    safetyUpdateListener.onSafetyInfoUpdate(mSafety, position);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}


