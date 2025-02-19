package com.example.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 1;
    private static final int PICK_IMAGE_CODE = 2;

    private ImageView imageView;
    private TextView ratingText;
    private TextView top1;
    private TextView one_hidden;
    private boolean isExpandedOne = false;
    private TextView top2;
    private TextView two_hidden;
    private boolean isExpandedTwo = false;
    private TextView top3;
    private TextView three_hidden;
    private boolean isExpandedThree = false;
    private TextView top4;
    private TextView four_hidden;
    private boolean isExpandedFour = false;
    private TextView top5;
    private TextView five_hidden;
    private boolean isExpandedFive = false;

    private static final String ONNX_TAG = "ONNXRuntime";
    private static final String CSV_TAG = "CSVReading";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();

        imageView = findViewById(R.id.imageView);
        ratingText = findViewById(R.id.ratingView);

        top1 = findViewById(R.id.top1);
        one_hidden = findViewById(R.id.top1_hidden);

        top2 = findViewById(R.id.top2);
        two_hidden = findViewById(R.id.top2_hidden);

        top3 = findViewById(R.id.top3);
        three_hidden = findViewById(R.id.top3_hidden);

        top4 = findViewById(R.id.top4);
        four_hidden = findViewById(R.id.top4_hidden);

        top5 = findViewById(R.id.top5);
        five_hidden = findViewById(R.id.top5_hidden);

        // checking for permissions
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // if permissions are not granted, requesting them
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }

        top1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedOne) {
                    one_hidden.setVisibility(View.GONE);

                    ratingText.setVisibility(View.VISIBLE);
                    top2.setVisibility(View.VISIBLE);
                    top3.setVisibility(View.VISIBLE);
                    top4.setVisibility(View.VISIBLE);
                    top5.setVisibility(View.VISIBLE);

                    isExpandedOne = false;
                } else {
                    one_hidden.setVisibility(View.VISIBLE);

                    ratingText.setVisibility(View.GONE);
                    top2.setVisibility(View.GONE);
                    top3.setVisibility(View.GONE);
                    top4.setVisibility(View.GONE);
                    top5.setVisibility(View.GONE);

                    one_hidden.setAlpha(0f);
                    one_hidden.animate().alpha(1f).setDuration(300);

                    isExpandedOne = true;

                    isExpandedTwo = false;
                    isExpandedThree = false;
                    isExpandedFour = false;
                    isExpandedFive = false;
                }
            }
        });

        top2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedTwo) {
                    two_hidden.setVisibility(View.GONE);

                    ratingText.setVisibility(View.VISIBLE);
                    top1.setVisibility(View.VISIBLE);
                    top3.setVisibility(View.VISIBLE);
                    top4.setVisibility(View.VISIBLE);
                    top5.setVisibility(View.VISIBLE);

                    isExpandedTwo = false;
                } else {
                    two_hidden.setVisibility(View.VISIBLE);

                    ratingText.setVisibility(View.GONE);
                    top1.setVisibility(View.GONE);
                    top3.setVisibility(View.GONE);
                    top4.setVisibility(View.GONE);
                    top5.setVisibility(View.GONE);

                    two_hidden.setAlpha(0f);
                    two_hidden.animate().alpha(1f).setDuration(300);

                    isExpandedTwo = true;

                    isExpandedOne = false;
                    isExpandedThree = false;
                    isExpandedFour = false;
                    isExpandedFive = false;
                }
            }
        });

        top3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedThree) {
                    three_hidden.setVisibility(View.GONE);

                    ratingText.setVisibility(View.VISIBLE);
                    top1.setVisibility(View.VISIBLE);
                    top2.setVisibility(View.VISIBLE);
                    top4.setVisibility(View.VISIBLE);
                    top5.setVisibility(View.VISIBLE);

                    isExpandedThree = false;
                } else {
                    three_hidden.setVisibility(View.VISIBLE);

                    ratingText.setVisibility(View.GONE);
                    top1.setVisibility(View.GONE);
                    top2.setVisibility(View.GONE);
                    top4.setVisibility(View.GONE);
                    top5.setVisibility(View.GONE);

                    three_hidden.setAlpha(0f);
                    three_hidden.animate().alpha(1f).setDuration(300);

                    isExpandedThree = true;
                    isExpandedOne = false;
                    isExpandedTwo = false;
                    isExpandedFour = false;
                    isExpandedFive = false;
                }
            }
        });

        top4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedFour) {
                    four_hidden.setVisibility(View.GONE);

                    ratingText.setVisibility(View.VISIBLE);
                    top1.setVisibility(View.VISIBLE);
                    top2.setVisibility(View.VISIBLE);
                    top3.setVisibility(View.VISIBLE);
                    top5.setVisibility(View.VISIBLE);

                    isExpandedFour = false;
                } else {
                    four_hidden.setVisibility(View.VISIBLE);

                    ratingText.setVisibility(View.GONE);
                    top1.setVisibility(View.GONE);
                    top2.setVisibility(View.GONE);
                    top3.setVisibility(View.GONE);
                    top5.setVisibility(View.GONE);

                    four_hidden.setAlpha(0f);
                    four_hidden.animate().alpha(1f).setDuration(300);

                    isExpandedFour = true;

                    isExpandedOne = false;
                    isExpandedTwo = false;
                    isExpandedThree = false;
                    isExpandedFive = false;
                }
            }
        });

        top5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandedFive) {
                    five_hidden.setVisibility(View.GONE);

                    ratingText.setVisibility(View.VISIBLE);
                    top1.setVisibility(View.VISIBLE);
                    top2.setVisibility(View.VISIBLE);
                    top3.setVisibility(View.VISIBLE);
                    top4.setVisibility(View.VISIBLE);
                    isExpandedFive = false;
                } else {
                    five_hidden.setVisibility(View.VISIBLE);

                    ratingText.setVisibility(View.GONE);
                    top1.setVisibility(View.GONE);
                    top2.setVisibility(View.GONE);
                    top3.setVisibility(View.GONE);
                    top4.setVisibility(View.GONE);

                    five_hidden.setAlpha(0f);
                    five_hidden.animate().alpha(1f).setDuration(300);

                    isExpandedFive = true;
                }
            }
        });
    }

    // method to open image chooser
    public void openImageChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    // method to open camera
    public void captureImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    // working with the results of camera activity or image chooser activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // results of camera activity
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
            ratingText.setVisibility(View.VISIBLE);

            modelProcessing(imageBitmap);
        }

        // results of image chooser activity
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {

                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                imageView.setImageBitmap(imageBitmap);
                ratingText.setVisibility(View.VISIBLE);

                modelProcessing(imageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String formatRow(String[] row) {
        return String.join(", ", row); // Joins the array elements with a comma separator
    }

    private List<String[]> readCSVFromAssets(String fileName) {
        List<String[]> data = new ArrayList<>();
        AssetManager assetManager = getAssets();  // Get AssetManager to access assets folder

        try (InputStream inputStream = assetManager.open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            String delimiter = ",";
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(delimiter);
                data.add(row);
            }
            Log.d(CSV_TAG, "CSV file reading succeeded!");

        } catch (IOException e) {
            Log.d(CSV_TAG, "CSV file reading failed!");
            e.printStackTrace();
        }

        return data;
    }

    private FloatBuffer preprocessBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        FloatBuffer floatBuffer = FloatBuffer.allocate(3 * width * height); // CHW format

        // Fill each channel separately
        for (int c = 0; c < 3; c++) { // Loop through channels: R, G, B
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = bitmap.getPixel(x, y);
                    if (c == 0) {
                        floatBuffer.put(Color.red(pixel) / 255.0f); // Red Channel
                    } else if (c == 1) {
                        floatBuffer.put(Color.green(pixel) / 255.0f); // Green Channel
                    } else {
                        floatBuffer.put(Color.blue(pixel) / 255.0f); // Blue Channel
                    }
                }
            }
        }
        floatBuffer.rewind();
        return floatBuffer;
    }


    private String loadModelFromAssets(String assetFileName) throws Exception {
        File modelFile = new File(getFilesDir(), assetFileName);
        if (!modelFile.exists()) {
            try (InputStream is = getAssets().open(assetFileName);
                 FileOutputStream fos = new FileOutputStream(modelFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
        }
        return modelFile.getAbsolutePath();
   }

    public Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    @SuppressLint("SetTextI18n")
    public void modelProcessing(Bitmap imageBitmap) {
        try {
            String modelPath = loadModelFromAssets("model.onnx");

            // Load and resize the Bitmap
            Bitmap resizedBitmap = resizeBitmap(imageBitmap, 224, 224);

            // Preprocess the Bitmap into a Tensor
            FloatBuffer inputBuffer = preprocessBitmap(resizedBitmap);

            // Initialize ONNX Runtime Environment
            OrtEnvironment env = OrtEnvironment.getEnvironment();
            OrtSession session = env.createSession(modelPath, new OrtSession.SessionOptions());

            // Prepare input tensor
            Map<String, OnnxTensor> inputs = new HashMap<>();
            inputs.put("image", OnnxTensor.createTensor(env, inputBuffer, new long[]{1, 3, 224, 224}));

            // Run inference
            OrtSession.Result result = session.run(inputs);

            // Get the output tensor
            OnnxTensor output = (OnnxTensor) result.get(0);

            // Retrieve the output as a 2D array
            float[][] outputData = (float[][]) output.getValue();

            // Access the first row (batch size is typically 1)
            float[] predictions = outputData[0];

            // getting "top" indices
            Integer[] indices = new Integer[predictions.length];

            // "argsorting the vector"
            for (int i = 0; i < predictions.length; i++) indices[i] = i;

            Arrays.sort(indices, (i, j) -> Float.compare(predictions[j], predictions[i]));

            int[] topIndices = {indices[0], indices[1], indices[2],indices[3], indices[4]};
            Log.d(ONNX_TAG, "Top Indices:" + Arrays.toString(topIndices));

            List<String[]> csvData = readCSVFromAssets("dishes_ext.csv");
            Log.d(ONNX_TAG, "CSV loaded. Rows: " + csvData.size());

            top1.setText(formatRow(csvData.get(topIndices[0])));
            top2.setText(formatRow(csvData.get(topIndices[1])));
            top3.setText(formatRow(csvData.get(topIndices[2])));
            top4.setText(formatRow(csvData.get(topIndices[3])));
            top5.setText(formatRow(csvData.get(topIndices[4])));
            Log.d(ONNX_TAG, "Main TextViews updated");

            Map<String, Dish> dishes = JsonParser.parseJson(this, "nutritional_data.json");

            if (dishes != null) {
                if (dishes.get(formatRow(csvData.get(topIndices[0]))) != null) {
                    one_hidden.setText(dishes.get(formatRow(csvData.get(topIndices[0]))).toString());
                }
                if (dishes.get(formatRow(csvData.get(topIndices[1]))) != null) {
                    two_hidden.setText(dishes.get(formatRow(csvData.get(topIndices[1]))).toString());
                }
                if (dishes.get(formatRow(csvData.get(topIndices[2]))) != null) {
                    three_hidden.setText(dishes.get(formatRow(csvData.get(topIndices[2]))).toString());
                }
                if (dishes.get(formatRow(csvData.get(topIndices[3]))) != null) {
                    four_hidden.setText(dishes.get(formatRow(csvData.get(topIndices[3]))).toString());
                }
                if (dishes.get(formatRow(csvData.get(topIndices[4]))) != null) {
                    five_hidden.setText(dishes.get(formatRow(csvData.get(topIndices[4]))).toString());
                }
            }

//            if (dishes != null) {
//                for (Map.Entry<String, Dish> entry : dishes.entrySet()) {
//                    String dishName = entry.getKey();
//                    Dish dish = entry.getValue();
//
//                    System.out.println("Dish: " + dishName);
//                    System.out.println("Details: " + dish);
//                }
//            }

        } catch (Exception e) {
            Log.e(ONNX_TAG, "Error during ONNX Runtime initialization", e);
        }
    }
}