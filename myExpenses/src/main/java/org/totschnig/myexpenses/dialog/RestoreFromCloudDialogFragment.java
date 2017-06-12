package org.totschnig.myexpenses.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.totschnig.myexpenses.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestoreFromCloudDialogFragment extends CommitSafeDialogFragment
    implements DialogInterface.OnClickListener {
  private static final String KEY_BACKUP_LIST = "backupList";
  private static final String KEY_SYNC_ACCOUNT_LIST = "syncAccountList";
  @BindView(R.id.tabs)
  protected TabLayout tabLayout;
  @BindView(R.id.backup_list)
  protected ListView backupList;
  @BindView(R.id.sync_account_list)
  protected ListView syncAccountList;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Activity ctx = getActivity();
    @SuppressLint("InflateParams")
    final View view = LayoutInflater.from(ctx).inflate(R.layout.restore_from_cloud, null);
    ButterKnife.bind(this, view);
    ArrayList<String> backups = getArguments().getStringArrayList(KEY_BACKUP_LIST);
    ArrayList<String> syncAccounts = getArguments().getStringArrayList(KEY_SYNC_ACCOUNT_LIST);
    if (backups != null && backups.size() > 0) {
      backupList.setAdapter(new ArrayAdapter<>(getActivity(),
          android.R.layout.simple_list_item_single_choice, backups));
      backupList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    } else {
      tabLayout.removeTabAt(0);
    }
    if (syncAccounts != null && syncAccounts.size() > 0) {
      syncAccountList.setAdapter(new ArrayAdapter<>(getActivity(),
          android.R.layout.simple_list_item_multiple_choice, syncAccounts));
    } else {
      tabLayout.removeTabAt(1);
    }
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        getContentForPosition(tab.getPosition()).setVisibility(View.VISIBLE);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        getContentForPosition(tab.getPosition()).setVisibility(View.GONE);
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    final AlertDialog dialog = new AlertDialog.Builder(ctx)
        .setTitle(R.string.onboarding_restore_from_cloud)
        .setView(view)
        .setPositiveButton(android.R.string.ok, this)
        .setNegativeButton(android.R.string.cancel,null)
        .create();
    dialog.setOnShowListener(new ButtonOnShowDisabler());
    return dialog;
  }

  private ListView getContentForPosition(int position) {
    if (position == 0) {
      return backupList;
    } else {
      return syncAccountList;
    }
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {

  }

  private void enableSubmit() {
    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
  }

  public static RestoreFromCloudDialogFragment newInstance(List<String> backupList, List<String> syncAccountList) {
    Bundle arguments = new Bundle(2);
    arguments.putStringArrayList(KEY_BACKUP_LIST, new ArrayList<>(backupList));
    arguments.putStringArrayList(KEY_SYNC_ACCOUNT_LIST, new ArrayList<>(syncAccountList));
    RestoreFromCloudDialogFragment fragment = new RestoreFromCloudDialogFragment();
    fragment.setArguments(arguments);
    return fragment;
  }
}
