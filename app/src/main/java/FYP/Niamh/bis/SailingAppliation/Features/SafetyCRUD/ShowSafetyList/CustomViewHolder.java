package FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.ShowSafetyList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import FYP.Niamh.bis.SailingAppliation.R;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView descriptionTextView;
    TextView issueTextView;
    TextView availableTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        issueTextView = itemView.findViewById(R.id.IssueTextView);
        availableTextView = itemView.findViewById(R.id.AvailableTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
