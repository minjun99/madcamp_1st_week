package com.example.madcamp_1st_week;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentGallery extends Fragment {
    View view;

    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;
    private Button btnCapture;
    private Button btnDisplay;

    public FragmentGallery() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gallery_fragment, container, false);
        btnCapture = (Button) view.findViewById(R.id.capturebtn_id);
        btnDisplay = (Button) view.findViewById(R.id.displaybtn_id);
        btnCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    captureImage(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    displayImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void displayImage() throws IOException {
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("image_path", currentImagePath);
        startActivity(intent);
    }

    public void captureImage(View view) throws IOException {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            File imageFile = null;
            imageFile = getImageFile();

            if(imageFile != null)
            {
                Uri imageUri = FileProvider.getUriForFile(getContext(), "com.example.madcamp_1st_week.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, IMAGE_REQUEST);
            }
         }

    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }
}
