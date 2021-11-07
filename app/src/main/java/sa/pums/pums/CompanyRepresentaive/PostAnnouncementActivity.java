package sa.pums.pums.CompanyRepresentaive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sa.pums.pums.Model.AnnouncementModel;
import sa.pums.pums.Model.CourseModel;
import sa.pums.pums.Model.dataModel;
import sa.pums.pums.R;

public class PostAnnouncementActivity extends AppCompatActivity {

    EditText about;
    ProgressDialog dialogM;
    DatabaseReference database;
    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_announcement);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);

        firebaseStorage = FirebaseStorage.getInstance();
        mainRef = firebaseStorage.getReference();
        userImagesRef = mainRef.child("images");
        database = FirebaseDatabase.getInstance("https://pums-9538d-default-rtdb.firebaseio.com/").getReference().
                child("Announcement");
        about = (EditText) findViewById(R.id.about);
        preview = (ImageView) findViewById(R.id.preview);
        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!about.getText().toString().trim().equals("") ) {
                    dialogM.show();
                    String key = database.push().getKey();

                    AnnouncementModel announcementModel=new  AnnouncementModel(key,about.getText().toString(), link);
                    database.child(key).setValue(announcementModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();

                            Toast.makeText(PostAnnouncementActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(PostAnnouncementActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(PostAnnouncementActivity.this, "Please see who entered the data correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    FirebaseStorage firebaseStorage;
    StorageReference mainRef, userImagesRef;
    String link;

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launchSomeActivity.launch(intent);

    ///  Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    ///  photoPickerIntent.setType("image/*");
    ///  startActivityForResult(photoPickerIntent, PICK_IMG_REQUEST);
    }

  /*  @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        try {

            if (resultCode == RESULT_OK) {
                final Uri imageUri = data.getData();
                upload(imageUri);
            } else {
                Toast.makeText(this, "لم يتم اختيار صورة :/", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
        }

    }*/
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        final Uri imageUri = data.getData();
                        upload(imageUri);
                    }
                }
            });
    private void upload(Uri uri) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        final String imageName = UUID.randomUUID().toString() + ".jpg";


        userImagesRef.child(imageName).putFile(uri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage(progress + "");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {

                            Task<Uri> urlTask = task.getResult().getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();

                            link = String.valueOf(downloadUrl);
                            String path = task.getResult().getStorage().getPath();

                        //    photoET.setText("" + task.getResult().getStorage().getName());
                            Toast.makeText(PostAnnouncementActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PostAnnouncementActivity.this, "Upload Failed :( " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}