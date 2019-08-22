package ua.com.webacademy.beginnerslection17.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import ua.com.webacademy.beginnerslection17.R;
import ua.com.webacademy.beginnerslection17.service.APIService;

public abstract class APIFragment extends Fragment {
    protected APIService service;

    public APIFragment(APIService service) {
        this.service = service;
    }


    //Progress
    private View getProgressView() {
        return getView().findViewById(R.id.viewProgress);
    }

    protected void showProgress() {
        View progress = getProgressView();
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    protected void hideProgress() {
        View progress = getProgressView();
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }
}
