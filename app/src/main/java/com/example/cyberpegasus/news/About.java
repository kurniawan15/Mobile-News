package com.example.cyberpegasus.news;


import java.util.Calendar;
import android.icu.util.RangeValueIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element adsElement = new Element();
        adsElement.setTitle("Sembada Karya Mandiri");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.sembadakaryamandiri_logo)
                .addItem(new Element().setTitle("Version 1.0"))
                .setDescription("PT. Sembada Karya Mandiri merupakan perusahaan yang bergerak dibidang teknologi informasi dan sistem kendali, yang menawarkan jasa perancangan dan pembangunan sistem elektronik. Produk yang kami hasilkan merupakan karya anak bangsa.    \n" +
                        "\n" +
                        "PT. Sembada Karya Mandiri is Information Technology and Control System based Company, which offers services in designing and building electronic system. Supported by competent people. PT. Sembada Karya Mandiri creating Internationally competitive products.    ")
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("info@sembadakaryamandiri.co.id")
                .addWebsite("http://sembadakaryamandiri.co.id/")
                .addFacebook("PT. Sembada Karya Mandiri")
                .addTwitter("sembadakrymndr")
                .addYoutube("SEMBADA KARYA MANDIRI PT")
                .create();

        setContentView(aboutPage);
    }

    private Element createCopyright(){
        final Element copyright = new Element();
        final String copyrightString = String.format("Copyright %d by Sembada Karya Mandiri", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view ){
                Toast.makeText(About.this,copyrightString,Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}
