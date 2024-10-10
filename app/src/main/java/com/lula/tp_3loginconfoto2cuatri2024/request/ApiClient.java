package com.lula.tp_3loginconfoto2cuatri2024.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.lula.tp_3loginconfoto2cuatri2024.modelo.Usuario;

import java.io.*;

public class ApiClient {
    private static final String nameFile = "usuario.dat";
    public static void Guardar(Context context, Usuario usuario){
        File archivo = new File(context.getFilesDir(), nameFile);
        try (FileOutputStream fos = new FileOutputStream(archivo);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeUTF(usuario.getDocumento());
            dos.writeUTF(usuario.getApellido());
            dos.writeUTF(usuario.getNombre());
            dos.writeUTF(usuario.getMail());
            dos.writeUTF(usuario.getPassword());
            dos.writeUTF(usuario.getFoto());

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error de archivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error IO", Toast.LENGTH_LONG).show();
        }
    }

    public static Usuario Leer(Context context){
        Usuario usuario = null;
        File archivo = new File(context.getFilesDir(), nameFile);

        try (FileInputStream fis = new FileInputStream(archivo);
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream dis = new DataInputStream(bis);){

            usuario = new Usuario();
            usuario.setDocumento(dis.readUTF());
            usuario.setApellido(dis.readUTF());
            usuario.setNombre(dis.readUTF());
            usuario.setMail(dis.readUTF());
            usuario.setPassword(dis.readUTF());
            usuario.setFoto(dis.readUTF());

        } catch (FileNotFoundException fnfe) {
            Toast.makeText(context, "Error de Archivo", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            Toast.makeText(context, "Error de IO", Toast.LENGTH_SHORT).show();
        }
        return usuario;
    }

    public static boolean Login(Context context, String mail, String password){
        boolean result = false;
        Usuario usuario = Leer(context);

        if(usuario != null && usuario.getMail().equals(mail) && usuario.getPassword().equals(password)){
            result = true;
        }
        return result;
    }

    public static Bitmap redimensionarBitmap(Context context, Uri imageUri) {
        Bitmap resizedBitmap= null;
        try(InputStream inputStream = context.getContentResolver().openInputStream(imageUri);) {

            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
            resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 400, 400, true);
        }
        catch (IOException ex){
            Toast.makeText(context, "Error de FileNotFoundExeption", Toast.LENGTH_SHORT).show();
        }

        return resizedBitmap;
    }

    public static Uri guardarBitmapYObtenerUri(Context context, Bitmap bitmap)  {
        // Guardar el Bitmap en almacenamiento externo
        File directory = new File(context.getFilesDir(), "imagenes");
        if (!directory.exists()) {
            directory.mkdirs(); // Crear la carpeta si no existe
        }

        // Crear el archivo para la imagen
        File file = new File(directory, "fotoPerfilUsuario.jpg.jpg");
        try(FileOutputStream outputStream = new FileOutputStream(file)){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Guardar el bitmap como JPEG
        }
        catch (IOException ex){
            Toast.makeText(context, "Error de IOException", Toast.LENGTH_SHORT).show();
        }

        // Devolver la Uri del archivo guardado
        return Uri.fromFile(file);
    }

    public static Uri redimensionarYGuardarImagen(Context context, Uri imageUri){
        // Redimensionar la imagen
        Bitmap resizedBitmap = redimensionarBitmap(context, imageUri);

        // Guardar el Bitmap redimensionado y obtener su Uri
        return guardarBitmapYObtenerUri(context, resizedBitmap);
    }
}
