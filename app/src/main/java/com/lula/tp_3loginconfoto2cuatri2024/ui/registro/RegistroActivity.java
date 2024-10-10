package com.lula.tp_3loginconfoto2cuatri2024.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lula.tp_3loginconfoto2cuatri2024.R;
import com.lula.tp_3loginconfoto2cuatri2024.databinding.ActivityRegistroBinding;
import com.lula.tp_3loginconfoto2cuatri2024.modelo.Usuario;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding bind;
    private RegistroActivityViewModel vm;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = ActivityRegistroBinding.inflate(getLayoutInflater());

        setContentView(bind.getRoot());
        abrirGaleria();
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);

        vm.getUsuarioMutable().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                bind.etApellido.setText(usuario.getApellido());
                bind.etDocumento.setText(usuario.getDocumento());
                bind.etNombre.setText(usuario.getNombre());
                bind.etEmailRegistro.setText(usuario.getMail());
                bind.etPasswordRegistro.setText(usuario.getPassword());
                bind.ivFotoPerfil.setImageURI(Uri.parse(usuario.getFoto()));
            }
        });

        bind.btGuardar.setOnClickListener(view -> {
            String documento = bind.etDocumento.getText().toString();
            String apellido = bind.etApellido.getText().toString();
            String nombre = bind.etNombre.getText().toString();
            String email = bind.etEmailRegistro.getText().toString();
            String password = bind.etPasswordRegistro.getText().toString();

            vm.Guardar(documento, apellido, nombre, email, password);

        });

        vm.Leer(getIntent());

        bind.btTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        vm.getUriMutable().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                bind.ivFotoPerfil.setImageURI(uri);
            }
        });

    }

    private void abrirGaleria(){


        intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);


            }
        });

    }
}