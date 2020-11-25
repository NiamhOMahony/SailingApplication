package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.ShowSafetyList;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import FYP.Niamh.bis.SailingAppliation.Database.DatabaseQueryClass;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.Safety;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.UpdateSafetyInfo.SafetyUpdateDialogFragment;
import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.UpdateSafetyInfo.SafetyUpdateListener;
import FYP.Niamh.bis.SailingAppliation.R;
import FYP.Niamh.bis.SailingAppliation.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class SafetyListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Safety> safetyList;
    private DatabaseQueryClass databaseQueryClass;

    public SafetyListRecyclerViewAdapter(Context context, List<Safety> safetyList) {
        this.context = context;
        this.safetyList = safetyList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_safety, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Safety safety = safetyList.get(position);

        holder.nameTextView.setText(safety.getName());
        holder.descriptionTextView.setText(safety.getDescription());
        holder.issueTextView.setText(safety.getIssue());
        holder.availableTextView.setText(safety.getAvailable());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this safety equipment?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteSafety(itemPosition);
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
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SafetyUpdateDialogFragment safetyUpdateDialogFragment = SafetyUpdateDialogFragment.newInstance(safety.getId(), itemPosition, new SafetyUpdateListener() {
                    @Override
                    public void onSafetyInfoUpdate(Safety safety, int position) {
                        safetyList.set(position, safety);
                        notifyDataSetChanged();
                    }
                });
                safetyUpdateDialogFragment.show(((SafetyListActivity) context).getSupportFragmentManager(), Config.UPDATE_SAFETY);
            }
        });

    }

    private void deleteSafety(int position) {
        Safety safety = safetyList.get(position);
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteSafetyById(safety.getId());

        if(isDeleted) {
            safetyList.remove(safety);
            notifyDataSetChanged();
            ((SafetyListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return safetyList.size();
    }
}
