package br.com.coffeebeansdev.waterlevelmobile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.SQLException;

import br.com.coffeebeans.exception.ClientWebServiceException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.util.ClientWebService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fachada fachada;
    private TextView textViewEmail;
    private TextView textViewNome;
    private ImageView imageViewFoto;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewEmail = (TextView) findViewById(R.id.tvEmail);
        textViewNome = (TextView) findViewById(R.id.tvNome);
        imageViewFoto = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    fachada = Fachada.getInstance(MainActivity.this);
                    if (fachada.getUsuarioLogado() == null) {
                        sair();
                    } else {
                        textViewNome.setText(fachada.getUsuarioLogado().getNome());
                        textViewEmail.setText(fachada.getUsuarioLogado().getEmail());
                        if (fachada.getUsuarioLogado().getFoto() != null)
                            imageViewFoto.setImageURI(Uri.fromFile(new File(fachada.getUsuarioLogado().getFoto())));
                        else
                            imageViewFoto.setImageResource(R.drawable.ic_user);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    Log.i("ERRO", e.getMessage());
//                    Toast.makeText(MainActivity.this, "Erro ao instanciar fachada\n" + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
                }
            }
        }).start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(FloatingActionButton.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_inicio);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        ClientWebService cws = new ClientWebService();
        try {
            cws.exemploGetUsers();
        } catch (ClientWebServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            sair();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        progressBar.setVisibility(View.VISIBLE);
        Fragment frgmt = null;
        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            frgmt = new FragmentHome();
            setTitle("Inicio");
        } else if (id == R.id.nav_usuario) {
            frgmt = new FragmentUsuario();
            setTitle("Usuarios");
        } else if (id == R.id.nav_atividade) {
            frgmt = new FragmentAtividade();
            setTitle("Atividades");
        } else if (id == R.id.nav_config) {
            frgmt = new FragmentConfig();
            setTitle("Configurações");
        } else if (id == R.id.nav_iniciar_atividade) {
            frgmt = new FragmentIniciarAtividade();
            setTitle("Selecione a Atividade");
        } else if (id == R.id.nav_rank) {
            frgmt = new FragmentRank();
            setTitle("Rank");
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.content_main, frgmt)
                .commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        progressBar.setVisibility(View.INVISIBLE);
        return true;
    }

    public void sair() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
        try {
            fachada.logout();
        } catch (SQLException e) {
            Log.i("ERRO", e.getMessage());
            Toast.makeText(this, "Erro ao fazer logof\n" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (DAOException e) {
            Log.i("ERRO", e.getMessage());
            Toast.makeText(this, "Erro ao fazer logof\n" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
