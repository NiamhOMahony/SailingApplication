package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.ShowSafetyList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import FYP.Niamh.bis.SailingAppliation.Database.DatabaseQueryClass;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.Safety;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.SafetyCreateDialogFragment;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.SafetyCreateListener;
import FYP.Niamh.bis.SailingAppliation.R;
import FYP.Niamh.bis.SailingAppliation.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class SafetyListActivity extends AppCompatActivity implements SafetyCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Safety> safetyList = new ArrayList<>();

    private TextView safetyListEmptyTextView;
    private RecyclerView recyclerView;
    private SafetyListRecyclerViewAdapter safetyListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        safetyListEmptyTextView = findViewById(R.id.emptyListTextView);

        safetyList.addAll(databaseQueryClass.getAllSafety());

        safetyListRecyclerViewAdapter = new SafetyListRecyclerViewAdapter(this, safetyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(safetyListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSafetyCreateDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //printSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all safetys?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllSafetys();
                            if(isAllDeleted){
                                safetyList.clear();
                                safetyListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(safetyList.isEmpty())
            safetyListEmptyTextView.setVisibility(View.VISIBLE);
        else
            safetyListEmptyTextView.setVisibility(View.GONE);
        //   printSummary();
    }

    private void openSafetyCreateDialog() {
        SafetyCreateDialogFragment safetyCreateDialogFragment = SafetyCreateDialogFragment.newInstance("Create Safety", this);
        safetyCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_SAFETY);
    }

   /* private void printSummary() {
        long safetyNum = databaseQueryClass.getNumberOfSafety();

       // summaryTextView.setText(getResources().getString((int) safetyNum));
    }*/

    @Override
    public void onSafetyCreated(Safety safety) {
        safetyList.add(safety);
        safetyListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Log.d("NIAMH_FYP","Safety: "+safety.getName());
    }

}
