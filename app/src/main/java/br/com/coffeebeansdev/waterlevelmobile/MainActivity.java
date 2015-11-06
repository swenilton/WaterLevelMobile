package br.com.coffeebeansdev.waterlevelmobile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.usuario.Usuario;
import br.com.coffeebeans.usuario.UsuarioDAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fachada fachada;
    private TextView textViewEmail;
    private TextView textViewNome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewEmail = (TextView) findViewById(R.id.tvEmail);
        textViewNome = (TextView) findViewById(R.id.tvNome);
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

        try{
            fachada = Fachada.getInstance(this);
            textViewNome.setText(fachada.getUsuarioLogado().getNome());
            textViewEmail.setText(fachada.getUsuarioLogado().getEmail());

        } catch (Exception e){
            Log.i("ERRO", e.getMessage());
            Toast.makeText(this, "Erro ao instanciar fachada\n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment frgmt = null;
        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            frgmt = new FragmentHome();
            setTitle("Inicio");
        } else if (id == R.id.nav_usuario) {
            frgmt = new FragmentUsuario();
            setTitle("Usuario");
        } else if (id == R.id.nav_atividade) {
            frgmt = new FragmentAtividade();
            setTitle("Atividade");
        } else if (id == R.id.nav_config) {
            frgmt = new FragmentConfig();
            setTitle("Configurações");
        } else if (id == R.id.nav_iniciar_atividade) {
            frgmt = new FragmentIniciarAtividade();
            setTitle("Iniciar Atividade");
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
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 12){
            if(resultCode == RESULT_OK){

                Uri imagemSelecionada = intent.getData();

                String[] colunas = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                cursor.moveToFirst();

                int indexColuna = cursor.getColumnIndex(colunas[0]);
                String pathImg = cursor.getString(indexColuna);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                DialogInserirUsuario.setImg(bitmap);

            }
        }
    }
}
