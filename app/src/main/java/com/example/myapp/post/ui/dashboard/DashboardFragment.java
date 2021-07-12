package com.example.myapp.post.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapp.R;
import com.example.myapp.databinding.FragmentDashboardBinding;
import com.example.myapp.logIn.ui.LogInActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button button_acc = root.findViewById(R.id.accountmanage);
        button_acc.setOnClickListener(v -> {
            startActivity(new Intent(root.getContext(), AccountManageActivity.class));});
        Button button_email = root.findViewById(R.id.emailchange);
        button_email.setOnClickListener(v -> {
            Toast.makeText(root.getContext(), "暂时无法提供该服务", Toast.LENGTH_SHORT).show();});
        Button button_pass = root.findViewById(R.id.password_change);
        button_pass.setOnClickListener(v -> {
            startActivity(new Intent(root.getContext(), PasswordChangeActivity.class));});
        Button button_notice = root.findViewById(R.id.notice);
        button_notice.setOnClickListener(v -> {
            startActivity(new Intent(root.getContext(), NoticeActivity.class));});
        root.findViewById(R.id.logout).setOnClickListener(v -> {
            startActivity(new Intent(root.getContext(), LogInActivity.class));
        });

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}