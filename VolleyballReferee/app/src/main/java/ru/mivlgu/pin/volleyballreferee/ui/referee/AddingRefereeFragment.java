package ru.mivlgu.pin.volleyballreferee.ui.referee;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.mivlgu.pin.volleyballreferee.App;
import ru.mivlgu.pin.volleyballreferee.R;
import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.CategoryDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.RefereeDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Category;
import ru.mivlgu.pin.volleyballreferee.database.entities.Referee;
import ru.mivlgu.pin.volleyballreferee.databinding.AddingRefereeBinding;

public class AddingRefereeFragment extends Fragment {

    private AddingRefereeBinding binding;
    private ArrayAdapter<String> adapter;
    private AppDatabase db;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = AddingRefereeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addRefereeBtn.setOnClickListener(this :: onAdd);
        binding.loadPhotoIv.setOnClickListener(this :: onLoadPhoto);

        db = App.getInstance().getDatabase();
        CategoryDao categoryDao = db.categoryDao();
        List<Category> categories = categoryDao.getAll();
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.name);
        }
        final String[] items = names.toArray(new String[0]);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.refereeCategorySpinner.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    private void onAdd (View view) {
        if (binding.refereeFullNameEt.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Ошибка при добавлении судьи!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.teamCityEt.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Ошибка при добавлении судьи!", Toast.LENGTH_SHORT).show();
            return;
        }

        Referee referee = new Referee();
        referee.fullName = binding.refereeFullNameEt.getText().toString();
        referee.city = binding.teamCityEt.getText().toString();
        referee.categoryId = (int) binding.refereeCategorySpinner.getSelectedItemId() + 1;

        referee.photo = "Судья " + referee.fullName;

        try (FileOutputStream fos = getActivity().openFileOutput(referee.photo, MODE_PRIVATE)) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            RefereeDao refereeDao = db.refereeDao();
            refereeDao.insert(referee);

            NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            host.navigate(R.id.navigation_referees);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка при добавлении судьи!", Toast.LENGTH_LONG).show();
        }
    }

    private final int REQUEST_CODE_PHOTO = 100;
    private final int REQUEST_CAMERA_PERMISSION_STATE = 200;

    private void onLoadPhoto (View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.CAMERA
        );
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                    new String[] { Manifest.permission.CAMERA },
                    REQUEST_CAMERA_PERMISSION_STATE
            );
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    private Bitmap image;

    @Override
    public void onActivityResult (int requestCode, int resultCode,
                                  @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_PHOTO
                && resultCode == -1) {
            if (data != null) {
                Bundle extras = data.getExtras();
                image = (Bitmap) extras.get("data");
            }
        }
    }
}