package m19.hsinhanw.louisacoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgGallery = findViewById(R.id.logoImageView);
        int gallery[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
        animate(imgGallery, gallery, 0, true);

        TextView phone = (TextView)findViewById(R.id.phone);
        phone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri callIntentUri = Uri.parse("tel:"+getResources().getString(R.string.phone));
                Intent callIntent = new Intent(Intent.ACTION_CALL, callIntentUri);
                startActivity(callIntent);
            }
        });

        TextView location = (TextView)findViewById(R.id.address);
        location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(getResources().getString(R.string.gmapValue));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(mapIntent);
                }
            }
        });

    }

    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever){
                        animate(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }
}
