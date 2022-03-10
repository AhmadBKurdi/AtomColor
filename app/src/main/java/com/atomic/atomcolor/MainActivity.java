package com.atomic.atomcolor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    private TextView color1, color2, color3, color4, color5, color6;
    private Button button;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.color1 = findViewById(R.id.color1);
        this.color2 = findViewById(R.id.color2);
        this.color3 = findViewById(R.id.color3);
        this.color4 = findViewById(R.id.color4);
        this.color5 = findViewById(R.id.color5);
        this.color6 = findViewById(R.id.color6);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.logo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getImage(); }
        });
    }

    @Override
    public void onBackPressed() { recreate(); }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                createPalette(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getHex(String string) {
        int x = string.indexOf("#");
        return string.substring(x + 3, x + 9);
    }

    private String getRgb(String string) {
        int red = Integer.parseInt(string.substring(0, 2), 16);
        int green = Integer.parseInt(string.substring(2, 4), 16);
        int blue = Integer.parseInt(string.substring(4, 6), 16);

        return ("RGB: (" + red + ", " + green + ", " + blue + ")");
    }

    private void createPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {

                button.setVisibility(View.INVISIBLE);

                Palette.Swatch vibrant = palette.getVibrantSwatch();
                Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();
                Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();
                Palette.Swatch muted = palette.getMutedSwatch();
                Palette.Swatch mutedDark = palette.getDarkMutedSwatch();
                Palette.Swatch mutedLight = palette.getLightMutedSwatch();

                if (vibrant != null) {
                    color1.setBackgroundColor(vibrant.getRgb());
                    color1.setTextColor(vibrant.getTitleTextColor());
                    color1.setText(getRgb(getHex(vibrant.toString())) + ", Hex: #" + getHex(vibrant.toString()));
                } else {
                    color1.setVisibility(View.GONE);
                }

                if (vibrantDark != null) {
                    color2.setBackgroundColor(vibrantDark.getRgb());
                    color2.setTextColor(vibrantDark.getTitleTextColor());
                    color2.setText(getRgb(getHex(vibrantDark.toString())) + ", Hex: #" + getHex(vibrantDark.toString()));
                } else {
                    color2.setVisibility(View.GONE);
                }

                if (vibrantLight != null) {
                    color3.setBackgroundColor(vibrantLight.getRgb());
                    color3.setTextColor(vibrantLight.getTitleTextColor());
                    color3.setText(getRgb(getHex(vibrantLight.toString())) + ", Hex: #" + getHex(vibrantLight.toString()));
                } else {
                    color3.setVisibility(View.GONE);
                }

                if (muted != null) {
                    color4.setBackgroundColor(muted.getRgb());
                    color4.setTextColor(muted.getTitleTextColor());
                    color4.setText(getRgb(getHex(muted.toString())) + ", Hex: #" + getHex(muted.toString()));
                } else {
                    color4.setVisibility(View.GONE);
                }

                if (mutedDark != null) {
                    color5.setBackgroundColor(mutedDark.getRgb());
                    color5.setTextColor(mutedDark.getTitleTextColor());
                    color5.setText(getRgb(getHex(mutedDark.toString())) + ", Hex: #" + getHex(mutedDark.toString()));
                } else {
                    color5.setVisibility(View.GONE);
                }

                if (mutedLight != null) {
                    color6.setBackgroundColor(mutedLight.getRgb());
                    color6.setTextColor(mutedLight.getTitleTextColor());
                    color6.setText(getRgb(getHex(mutedLight.toString())) + ", Hex: #" + getHex(mutedLight.toString()));
                } else {
                    color6.setVisibility(View.GONE);
                }
            }
        });
    }
}