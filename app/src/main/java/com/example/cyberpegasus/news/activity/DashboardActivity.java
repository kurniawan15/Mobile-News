package com.example.cyberpegasus.news.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.adapter.DashboardAdapter;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.network.BaseAPIService;
import com.example.cyberpegasus.news.network.RetrofitInstance;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppBaseActivity implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    FloatingActionButton floatingButton;
    TokenManager tokenManager;

    ArrayList<Data> list;

    public static Button btnFinishFilter;

    public static EditText wktDari, wktSampai;

    Calendar myCalendar = Calendar.getInstance();

    public static boolean filterOpen = false;

    public static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tokenManager=new TokenManager(getApplicationContext());
        tokenManager.checkLogin();
        //Date expiresAt= tokenManager.tellExpire();
        //Toast.makeText(getApplicationContext(),"Token habis sampai :"+expiresAt.toString(),Toast.LENGTH_SHORT).show();

        HashMap<String,String> user =tokenManager.getDetailLogin();
        String username=user.get(TokenManager.KEY_USER_NAME);
        String jwttoken=user.get(TokenManager.KEY_JWT_TOKEN);




        //Data dummy untuk mencoba fitur Search
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            d1 = sdf.parse("2018/02/21");
            d2 = sdf.parse("2018/06/21");
            d3 = sdf.parse("2018/07/21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        list = new ArrayList<>();
        Data data1 = new Data(d3, d3, "gerry","Teknologi", "Teknologi", "coba1", null);
        Data data2 = new Data(d1, d1, "gerry","Kesehatan", "Kesehatan", "coba2", null);
        Data data3 = new Data(d2, d2, "gerry","Olahraga", "Olahraga", "coba3", null);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        generateDataList(list);

        floatingButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        floatingButton.setImageResource(R.drawable.ic_action_pass);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(DashboardActivity.this, FormActivity.class);
                startActivity(formIntent);
                active = false;
            }
        });

        final RelativeLayout filter = (RelativeLayout) findViewById(R.id.filterLayout);

        if (filterOpen) {
            Animation animationLayout = AnimationUtils.loadAnimation(this, R.anim.animation);
            //Toast.makeText(this, "You clicked filter open", Toast.LENGTH_LONG).show();
            //Untuk animasi filter
            animateOpen(animationLayout, filter);
        }

        final Spinner kategoriSpinner = findViewById(R.id.dropdownFilterKategori);
        final Spinner subKategori1Spinner = findViewById(R.id.dropdownFilterSubKategori1);
        final Spinner subKategori2Spinner = findViewById(R.id.dropdownFilterSubKategori2);

        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapterKategori);
        kategoriSpinner.setSelection(0);

        kategoriSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()) {
                    case "Kriminal":
                        ArrayAdapter<CharSequence> adapterSubKriminal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kriminal,
                                android.R.layout.simple_spinner_item);
                        adapterSubKriminal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKriminal);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik":
                        ArrayAdapter<CharSequence> adapterSubKonflik = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik,
                                android.R.layout.simple_spinner_item);
                        adapterSubKonflik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKonflik);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Pelanggaran Kedaulatan":
                        ArrayAdapter<CharSequence> adapterSubPelKedaulatan = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_pelanggaran_kedaulatan,
                                android.R.layout.simple_spinner_item);
                        adapterSubPelKedaulatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubPelKedaulatan);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Kecelakaan":
                        ArrayAdapter<CharSequence> adapterSubKecelakaan = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kecelakaan,
                                android.R.layout.simple_spinner_item);
                        adapterSubKecelakaan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubKecelakaan);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana":
                        ArrayAdapter<CharSequence> adapterSubBencana = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori1Spinner.setAdapter(adapterSubBencana);
                        subKategori1Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    default:
                        subKategori1Spinner.setAdapter(null);
                        subKategori1Spinner.setVisibility(Spinner.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subKategori1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()) {
                    case "Kejahatan Kekayaan Negara":
                        ArrayAdapter<CharSequence> adapterSubKKN = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_kejahatan_kekayaan_negara,
                                android.R.layout.simple_spinner_item);
                        adapterSubKKN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubKKN);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana Ulah Manusia":
                        ArrayAdapter<CharSequence> adapterSubBencanaManusia = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana_ulah_manusia,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencanaManusia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubBencanaManusia);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Bencana Alam":
                        ArrayAdapter<CharSequence> adapterSubBencanaAlam = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_bencana_alam,
                                android.R.layout.simple_spinner_item);
                        adapterSubBencanaAlam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterSubBencanaAlam);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Bersenjata":
                        ArrayAdapter<CharSequence> adapterKonflikBersenjata = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_bersenjata,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikBersenjata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikBersenjata);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Horisontal":
                        ArrayAdapter<CharSequence> adapterKonflikHorisontal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_horisontal,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikHorisontal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikHorisontal);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    case "Konflik Vertikal":
                        ArrayAdapter<CharSequence> adapterKonflikVertikal = ArrayAdapter.createFromResource(view.getContext(), R.array.sub_konflik_vertikal,
                                android.R.layout.simple_spinner_item);
                        adapterKonflikVertikal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subKategori2Spinner.setAdapter(adapterKonflikVertikal);
                        subKategori2Spinner.setVisibility(Spinner.VISIBLE);
                        break;

                    default:
                        subKategori2Spinner.setAdapter(null);
                        subKategori2Spinner.setVisibility(Spinner.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner urutanSpinner = findViewById(R.id.dropdownFilterUrutan);

        ArrayAdapter<CharSequence> adapterUrutan = ArrayAdapter.createFromResource(this, R.array.urutan,
                android.R.layout.simple_spinner_item);
        adapterUrutan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urutanSpinner.setAdapter(adapterUrutan);
        urutanSpinner.setSelection(0);

        btnFinishFilter = (Button) findViewById(R.id.buttonFinishFilter);
        btnFinishFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Untuk memeriksa apakah data dari spinner terambil
                String kategori = null;
                String subKategori1 = null;
                String subKategori2 = null;
                String urutan = null;
                Date dari = null;
                Date sampai = null;
                String tanggalDari = null;
                String tanggalSampai = null;

                kategori = kategoriSpinner.getSelectedItem().toString();

                try {
                    if (subKategori1Spinner.getSelectedItem().toString() != null) {
                        subKategori1 = subKategori1Spinner.getSelectedItem().toString();
                        if (!subKategori1.contains("All")) {
                            kategori = kategori + ", " + subKategori1;
                        }
                        if (subKategori2Spinner.getSelectedItem().toString() != null) {
                            subKategori2 = subKategori2Spinner.getSelectedItem().toString();
                            if (!subKategori2.contains("All")) {
                                kategori = kategori + ", " + subKategori2;
                            }
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(kategori);

                urutan = urutanSpinner.getSelectedItem().toString();
                try {
                    tanggalDari = wktDari.getText().toString();
                    tanggalSampai = wktSampai.getText().toString();
                    if (tanggalDari.equals("") && !tanggalSampai.equals("")) {
                        wktDari.setError("Tanggal tidak valid !");
                        Toast.makeText(DashboardActivity.this, "Tanggal belum diisi !", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (tanggalSampai.equals("") && !tanggalDari.equals("")) {
                        wktSampai.setError("Tanggal tidak valid !");
                        Toast.makeText(DashboardActivity.this, "Tanggal belum diisi !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dari = new SimpleDateFormat("yyyy/MM/dd").parse(tanggalDari);
                    sampai = new SimpleDateFormat("yyyy/MM/dd").parse(tanggalSampai);
                    if (dari.after(sampai)) {
                        wktSampai.setError("Tanggal tidak valid !");
                        Toast.makeText(DashboardActivity.this, "Tanggal tidak valid !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Filter terbaru, terlama
                if (urutan.equals("Terlama")) {
                    //Mengurutkan list berdasarkan tanggal berita dari yang terbaru ke yang terlama
                    Collections.sort(list, new Comparator<Data>() {
                        public int compare(Data o1, Data o2) {
                            if (o1.getDateBerita() == null || o2.getDateBerita() == null)
                                return 0;
                            return o1.getDateBerita().compareTo(o2.getDateBerita());
                        }
                    });
                }else {
                    //Mengurutkan list berdasarkan tanggal berita dari yang terbaru ke yang terlama
                    Collections.sort(list, new Comparator<Data>() {
                        public int compare(Data o1, Data o2) {
                            if (o1.getDateBerita() == null || o2.getDateBerita() == null)
                                return 0;
                            return o2.getDateBerita().compareTo(o1.getDateBerita());
                        }
                    });
                }

                //Filter berdasarkan kategori
                if (kategori != null) {
                    if (kategori.equals("Semua Kategori")) {
                        //Filter berdasarkan waktu
                        if (dari != null && sampai != null) {
                            ArrayList<Data> filtered = new ArrayList<>();
                            filtered.addAll(filterByDate(dari, sampai, list));
                            if (filtered.isEmpty()) {
                                Toast.makeText(DashboardActivity.this, "Tidak Ditemukan !", Toast.LENGTH_SHORT).show();
                            }else {
                                adapter = new DashboardAdapter(filtered);
                                recyclerView.setAdapter(adapter);
                                ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                            }
                        }else {
                            adapter = new DashboardAdapter(list);
                            recyclerView.setAdapter(adapter);
                            ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                        }
                    }else {
                        ArrayList<Data> filteredCategory = new ArrayList<>();
                        filteredCategory.addAll(filter(kategori, list));
                        if (filteredCategory.isEmpty()) {
                            Toast.makeText(DashboardActivity.this, "Tidak Ditemukan !", Toast.LENGTH_SHORT).show();
                        }else {
                            //Filter berdasarkan waktu
                            if (dari != null && sampai != null) {
                                ArrayList<Data> filteredDate = new ArrayList<>();
                                filteredDate.addAll(filterByDate(dari, sampai, filteredCategory));
                                if (filteredDate.isEmpty()) {
                                    Toast.makeText(DashboardActivity.this, "Tidak Ditemukan !", Toast.LENGTH_SHORT).show();
                                }else {
                                    adapter = new DashboardAdapter(filteredDate);
                                    recyclerView.setAdapter(adapter);
                                    ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                                }
                            }else {
                                adapter = new DashboardAdapter(filteredCategory);
                                recyclerView.setAdapter(adapter);
                                ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                            }
                        }
                    }
                }

                filterOpen = false;
                wktDari.setText(null);
                wktSampai.setText(null);
                wktDari.setError(null);
                wktSampai.setError(null);
                //Ketika tombol filter ditekan maka layout filter akan menutup dan melakukan proses di atas
                Animation animationLayout = AnimationUtils.loadAnimation(view.getContext(), R.anim.animation_close);
                animateClose(animationLayout, filter);
            }
        });

        wktDari = (EditText) findViewById(R.id.waktuDari);
        wktSampai = (EditText) findViewById(R.id.waktuSampai);

        final DatePickerDialog.OnDateSetListener dateDari = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel(true);
            }
        };

        final DatePickerDialog.OnDateSetListener dateSampai = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel(false);
            }
        };

        wktDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DashboardActivity.this, dateDari, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        wktSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DashboardActivity.this, dateSampai, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void generateDataList(ArrayList<Data> empDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new DashboardAdapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Create handle for the RetrofitInstance interface*/
        BaseAPIService service = RetrofitInstance.getRetrofitInstance().create(BaseAPIService.class);
        /*Call the method with parameter in the interface to get the data*/
        Call<DataList> call = service.getData();

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                list = response.body().getDataList();
                generateDataList(list);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Method untuk mem-filter list berdasarkan kategori berita
    public ArrayList<Data> filter(String kategori, ArrayList<Data> arrayList) {
        kategori = kategori.toUpperCase();
        ArrayList<Data> filtered = new ArrayList<>();
        for(Data data : arrayList){
            String kategoriDicari = data.getCategory().toUpperCase();
            if(kategoriDicari.contains(kategori))
                filtered.add(data);
        }
        //Toast.makeText(this, "Ditemukan " + filtered.size(), Toast.LENGTH_LONG).show();
        return filtered;
    }

    //Method untuk mem-filter list berdasarkan waktu berita
    public ArrayList<Data> filterByDate(Date dari, Date sampai, ArrayList<Data> arrayList) {
        ArrayList<Data> filtered = new ArrayList<>();
        for(Data data : arrayList){
            if ((data.getDateBerita().after(dari)||data.getDateBerita().equals(dari)) &&
                    (data.getDateBerita().before(sampai)||data.getDateBerita().equals(sampai))) {
                filtered.add(data);
            }
        }
        return filtered;
    }

    //Method untuk animasi menutup layout filter
    public void animateClose(Animation animationLayout, RelativeLayout filter) {
        //Untuk animasi filter menutup
        filter.setVisibility(LinearLayout.GONE);
        btnFinishFilter.setVisibility(Button.GONE);
        wktDari.setVisibility(EditText.GONE);
        wktSampai.setVisibility(EditText.GONE);
        animationLayout.setDuration(300);
        filter.setAnimation(animationLayout);
        filter.animate();
        animationLayout.start();
    }

    //Method untuk animasi membuka layout filter
    public void animateOpen(Animation animationLayout, RelativeLayout filter) {
        //Untuk animasi filter membuka
        filter.setVisibility(LinearLayout.VISIBLE);
        btnFinishFilter.setVisibility(Button.VISIBLE);
        wktDari.setVisibility(EditText.VISIBLE);
        wktSampai.setVisibility(EditText.VISIBLE);
        animationLayout.setDuration(300);
        filter.setAnimation(animationLayout);
        filter.animate();
        animationLayout.start();
        filter.requestFocus();
        filter.setClickable(true);
    }

    private void updateLabel(boolean flagWaktu) {
        String format = "YYYY/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        if (flagWaktu) {
            wktDari.setText(sdf.format(myCalendar.getTime()));
        }else {
            wktSampai.setText(sdf.format(myCalendar.getTime()));
        }
    }

    //Untuk membuat menu search dan filter yang ada pada toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //Method untuk search
    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toUpperCase();
        ArrayList<Data> newList = new ArrayList<>();
        for(Data data : list){
            String judul = data.getJudul().toUpperCase();
            if (judul.contains(newText))
                newList.add(data);
        }
        adapter = new DashboardAdapter(newList);
        recyclerView.setAdapter(adapter);
        ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        return true;
    }
}
