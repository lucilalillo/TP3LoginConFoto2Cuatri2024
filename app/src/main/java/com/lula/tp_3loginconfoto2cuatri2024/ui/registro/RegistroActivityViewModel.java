package com.lula.tp_3loginconfoto2cuatri2024.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lula.tp_3loginconfoto2cuatri2024.modelo.Usuario;
import com.lula.tp_3loginconfoto2cuatri2024.request.ApiClient;

public class RegistroActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> usuarioMutable;
    private MutableLiveData<Uri> uriMutableLiveData;
    private String fotoPerfilPath;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public LiveData<Usuario> getUsuarioMutable(){
        if(usuarioMutable == null){
            usuarioMutable = new MutableLiveData<Usuario>();
        }
        return usuarioMutable;
    }

    public void Guardar(String documento, String apellido, String nombre, String email, String password){

        Usuario usuario = new Usuario(documento, apellido, nombre, email, password, fotoPerfilPath);

        ApiClient.Guardar(context.getApplicationContext(), usuario);

        Toast.makeText(context, "Se guardaron los datos", Toast.LENGTH_SHORT).show();
    }

    public void Leer(Intent intent){
        boolean registro = intent.getBooleanExtra("registro", false);
        if(!registro){
            Usuario usuario = ApiClient.Leer(context);

            if(usuario != null){
                usuarioMutable.setValue(usuario);
            }
        }
    }

    public LiveData<Uri> getUriMutable(){

        if(uriMutableLiveData==null){
            uriMutableLiveData=new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){
            Intent data=result.getData();
            Uri uri=ApiClient.redimensionarYGuardarImagen(context, data.getData());
            fotoPerfilPath = uri.toString();
            uriMutableLiveData.setValue(uri);
        }
    }
}
