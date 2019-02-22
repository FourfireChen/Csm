package com.chuansongmen.base;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;

import org.w3c.dom.Text;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public <T extends View> T getView(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public TextView getTextView(@IdRes int textViewId) {
        return itemView.findViewById(textViewId);
    }

    public EditText getEditText(@IdRes int editTextId) {
        return itemView.findViewById(editTextId);
    }

    public ImageView getImageView(@IdRes int imageViewId) {
        return itemView.findViewById(imageViewId);
    }

    public Button getButton(@IdRes int buttonViewId) {
        return itemView.findViewById(buttonViewId);
    }

    BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
