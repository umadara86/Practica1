package com.example.ivan.audiolibros;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.ivan.audiolibros.fragments.DetalleFragment;
import com.example.ivan.audiolibros.fragments.PreferenciasFragment;
import com.example.ivan.audiolibros.fragments.SelectorFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private AdaptadorLibrosFiltro adaptador;
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SaveLastBook saveLastBook;
    //private LibroStorage libroStorage;
   // private MainController controller;
    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        int idContenedor = (findViewById(R.id.contenedor_pequeno) != null) ? R.id.contenedor_pequeno : R.id.contenedor_izquierdo;
        SelectorFragment primerFragment = new SelectorFragment();
        getFragmentManager().beginTransaction().add(idContenedor, primerFragment)
                .commit();

        //adaptador = ((Aplicacion) getApplicationContext()).getAdaptador();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //libroStorage  = LibroSharedPreferenceStorage.getInstance(this);
        saveLastBook = new SaveLastBook(LibroSharedPreferenceStorage.getInstance(this));
        presenter = new MainPresenter(saveLastBook,LibroSharedPreferenceStorage.getInstance(this), this);
        // Navigation Drawer
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); }

        drawer = (DrawerLayout) findViewById( R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string. drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            onBackPressed();
        }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "muestra última selección", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                presenter.clickFavoriteButton();

        }
        });

        //Pestañas
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                case 0: //Todos
                    LibrosSingleton.getInstance().getAdaptadorLibros().setNovedad(false);
                    LibrosSingleton.getInstance().getAdaptadorLibros().setLeido(false);
                    break;
                case 1: //Nuevos
                    LibrosSingleton.getInstance().getAdaptadorLibros().setNovedad(true);
                    LibrosSingleton.getInstance().getAdaptadorLibros().setLeido(false);
                    break;
                case 2: //Leidos
                    LibrosSingleton.getInstance().getAdaptadorLibros().setNovedad(false);
                    LibrosSingleton.getInstance().getAdaptadorLibros().setLeido(true);
                    break;
            }
                LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });


        // Nombre de usuario
        SharedPreferences pref = getSharedPreferences( "com.example.audiolibros_internal", MODE_PRIVATE);
        String name = pref.getString("name", null);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(String.format(getString(R.string.welcome_message), name));

        // Foto de usuario
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView fotoUsuario = (NetworkImageView) headerLayout.findViewById(R.id.imageView);
            Aplicacion aplicacion = (Aplicacion) getApplicationContext();
            fotoUsuario.setImageUrl(urlImagen.toString(), aplicacion.getLectorImagenes());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selector, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(LibrosSingleton.getInstance().getAdaptadorLibros());
        searchView.setOnQueryTextListener(searchObservable);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.nav_preferencias) {
            Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, PreferenciasActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.menu_preferencias) {
            Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
            abrePreferencias();
            return true;
        } else if (id == R.id.menu_acerca) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mensaje de Acerca De");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
        return true; }

        return super.onOptionsItemSelected(item);
    }

    private void mostrarFragmentDetalle(int id) {
        DetalleFragment detalleFragment = (DetalleFragment)
                getFragmentManager().findFragmentById(R.id.detalle_fragment);

        if (detalleFragment != null) {
            detalleFragment.ponInfoLibro(id);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle(); args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getFragmentManager().beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
        saveLastBook.execute(id);
    }

    public void mostrarDetalle(int id) {
        this.mostrarFragmentDetalle(id);
    }

    public void irUltimoVisitado() {
        presenter.clickFavoriteButton();
    }

    @Override
    public void mostrarNoUltimaVisita() {
        Toast.makeText(this, "Sin última vista",Toast.LENGTH_LONG).show();
    }

    public void mostrarElementos(boolean mostrar) {
        appBarLayout.setExpanded(mostrar);
        toggle.setDrawerIndicatorEnabled(mostrar);
        if (mostrar) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        tabs.setVisibility(View.VISIBLE);
        } else {
        tabs.setVisibility(View.GONE);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void abrePreferencias() {
        int idContenedor = (findViewById(R.id.contenedor_pequeno) != null) ? R.id.contenedor_pequeno : R.id.contenedor_izquierdo;
        PreferenciasFragment prefFragment = new PreferenciasFragment();
        getFragmentManager().beginTransaction()
                .replace(idContenedor, prefFragment).addToBackStack(null)
                .commit();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_todos) {
            LibrosSingleton.getInstance().getAdaptadorLibros().setGenero("");
            LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();
        } else if (id == R.id.nav_epico) {
            LibrosSingleton.getInstance().getAdaptadorLibros().setGenero(Libro.G_EPICO);
            LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            LibrosSingleton.getInstance().getAdaptadorLibros().setGenero(Libro.G_S_XIX);
            LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();
        } else if (id == R.id.nav_suspense) {
            LibrosSingleton.getInstance().getAdaptadorLibros().setGenero(Libro.G_SUSPENSE);
            LibrosSingleton.getInstance().getAdaptadorLibros().notifyDataSetChanged();
        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance().signOut(this)
            .addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
            public void onComplete(@NonNull Task<Void> task) {
                SharedPreferences pref = getSharedPreferences(
                    "com.example.audiolibros_internal", MODE_PRIVATE);
                    pref.edit().remove("provider").commit();
                    pref.edit().remove("email").commit();
                    pref.edit().remove("name").commit();
                Intent i = new Intent(MainActivity.this,CustomLoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i);
                finish(); }
            });
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
