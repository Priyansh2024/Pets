package com.example.pets;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.telephony.IccOpenLogicalChannelResponse;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pets.R;
import com.example.pets.data.PetContract.PetEntry;

public class PetCursorAdaptor extends CursorAdapter {

    /**
     * Constructs a new {@link PetCursorAdaptor}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public PetCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView genderTExtView = (TextView)view.findViewById(R.id.gender);
        TextView WeightTextView = (TextView) view.findViewById(R.id.weight);
        ImageView petImage = (ImageView) view.findViewById(R.id.pet_image);




        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
        int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
        int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
        int imageIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_IMAGE);



        // Read the pet attributes from the Cursor for the current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        int petGender = cursor.getInt(genderColumnIndex);
        String petweight = cursor.getString(weightColumnIndex);

        byte[] img = cursor.getBlob(imageIndex);
        Bitmap b = BitmapFactory.decodeByteArray(img , 0, img.length);
        petImage.setImageBitmap(b);


        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(petBreed)) {
            petBreed = context.getString(R.string.unknown_breed);
        }

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(petName);
        summaryTextView.setText(petBreed);
        if(petGender == 1){
            genderTExtView.setText("Male");

        }
        if(petGender == 2){
            genderTExtView.setText("Female");

        }
        if(petGender == 0){
            genderTExtView.setText("Unknown");
        }
        WeightTextView.setText(petweight + " Kg");

    }
}