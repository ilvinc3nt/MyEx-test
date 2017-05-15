package org.totschnig.myexpenses.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.totschnig.myexpenses.MyApplication;
import org.totschnig.myexpenses.R;
import org.totschnig.myexpenses.activity.ProtectedFragmentActivity;
import org.totschnig.myexpenses.preference.FontSizeDialogPreference;
import org.totschnig.myexpenses.preference.PrefKey;

public class OnboardingUiFragment extends Fragment {
  public static OnboardingUiFragment newInstance() {
    return new OnboardingUiFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    menu.add("Change language");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.onboarding_ui, container, false);
    int fontScale = PrefKey.UI_FONTSIZE.getInt(0);
    ((TextView) view.findViewById(R.id.font_size)).setText(FontSizeDialogPreference.getEntry(getActivity(), fontScale));
    SwitchCompat themeSwitch = (SwitchCompat) view.findViewById(R.id.theme);
    boolean isLight = MyApplication.getThemeType().equals(MyApplication.ThemeType.light);
    themeSwitch.setChecked(isLight);
    setContentDescriptonToThemeSwitch(themeSwitch, isLight);
    themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
      setContentDescriptonToThemeSwitch(buttonView, isChecked);
      PrefKey.UI_THEME_KEY.putString(
          (isChecked ? MyApplication.ThemeType.light : MyApplication.ThemeType.dark).name());
      ((ProtectedFragmentActivity) getActivity()).recreate();
    });
    return view;
  }

  private void setContentDescriptonToThemeSwitch(View themeSwitch, boolean isLight) {
    themeSwitch.setContentDescription(getString(
        isLight ? R.string.pref_ui_theme_light : R.string.pref_ui_theme_dark));
  }

}
