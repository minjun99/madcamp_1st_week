package com.example.madcamp_1st_week;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

public class FragmentGallery extends Fragment {
    View view;
    GridView gridView;
    ArrayList<File> imageList;

    public FragmentGallery() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gallery_fragment, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView_id);
        imageList = imageReader(Environment.getExternalStorageDirectory());
        gridView.setAdapter(new GridAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), FullImage.class);
                intent.putExtra("img", imageList.get(i).toString());
                startActivity(intent);
            }
        });

        return view;
    }

    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int i) {
            return imageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertView = null;

            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.gridview_item, viewGroup, false);
                ImageView grid_Image = (ImageView) convertView.findViewById(R.id.gridImage_id);
                grid_Image.setImageURI(Uri.parse(imageList.get(i).toString()));
            }
            return convertView;
        }
    }

    private ArrayList<File> imageReader(File externalStorageDirectory) {
        ArrayList<File> imageList = new ArrayList<>();

        File[] imageFiles = externalStorageDirectory.listFiles();

        for (int i = 0; i < imageFiles.length; i++) {
            if (imageFiles[i].isDirectory()) {
                imageList.addAll(imageReader(imageFiles[i]));
            } else {
                if(imageFiles[i].getName().endsWith("jpg") ||
                        imageFiles[i].getName().endsWith("png") ||
                        imageFiles[i].getName().endsWith("png")) {
                    imageList.add(imageFiles[i]);
                }
            }
        }

        return imageList;
    }
}
