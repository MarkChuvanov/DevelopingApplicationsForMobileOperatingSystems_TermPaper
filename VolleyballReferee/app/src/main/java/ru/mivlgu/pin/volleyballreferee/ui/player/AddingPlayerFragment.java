package ru.mivlgu.pin.volleyballreferee.ui.player;

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
import ru.mivlgu.pin.volleyballreferee.database.dao.PlayerDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.RoleDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.TeamDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Player;
import ru.mivlgu.pin.volleyballreferee.database.entities.Role;
import ru.mivlgu.pin.volleyballreferee.database.entities.Team;
import ru.mivlgu.pin.volleyballreferee.databinding.AddingPlayerBinding;

public class AddingPlayerFragment extends Fragment {

    private AddingPlayerBinding binding;
    private ArrayAdapter<String> adapter;
    private AppDatabase db;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
        binding = AddingPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addPlayerBtn.setOnClickListener(this :: onAdd);
        binding.loadPhotoIv.setOnClickListener(this :: onLoadPhoto);

        db = App.getInstance().getDatabase();
        RoleDao roleDao = db.roleDao();
        List<Role> roles = roleDao.getAll();
        List<String> names = new ArrayList<>();
        for (Role role : roles) {
            names.add(role.name);
        }
        String[] items = names.toArray(new String[0]);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.roleSpinner.setAdapter(adapter);

        TeamDao teamDao = db.teamDao();
        List<Team> teams = teamDao.getAll();
        names = new ArrayList<>();
        for (Team team : teams) {
            names.add(team.name);
        }
        items = names.toArray(new String[0]);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        binding.teamSpinner.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }

    public void onAdd (View view) {
        if (binding.playerFullNameEt.getText().toString().equals("")){
            Toast.makeText(getContext(), "Ошибка при добавлении игрока!", Toast.LENGTH_LONG).show();
            return;
        }

        TeamDao teamDao = db.teamDao();
        List<Team> teams = teamDao.getAll();
        if (teams.size() == 0) {
            Toast.makeText(getContext(), "Ошибка при добавлении игрока!", Toast.LENGTH_SHORT).show();
            return;
        }

        Player player = new Player();
        player.fullName = binding.playerFullNameEt.getText().toString();
        player.roleId = (int) binding.roleSpinner.getSelectedItemId() + 1;
        player.teamId = (int) binding.teamSpinner.getSelectedItemId() + 1;

        player.photo = "Игрок " + player.fullName;

        try (FileOutputStream fos = getActivity().openFileOutput(player.photo, MODE_PRIVATE)) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            PlayerDao playerDao = db.playerDao();
            playerDao.insert(player);

            NavController host = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            host.navigate(R.id.navigation_players);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка при добавлении игрока!", Toast.LENGTH_LONG).show();
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